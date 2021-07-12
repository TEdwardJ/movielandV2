package edu.ted.web.movieland.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.request.LoginRequest;
import edu.ted.web.movieland.service.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class DefaultSecurityService implements SecurityService {

    private final long userSessionLifeTime;

    private Cache<String, UserSession> userSessionCache;

    private final UserDao userDao;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public DefaultSecurityService(UserDao userDao, @Value("${user.session.cache.lifeInMilliSeconds:7200000}") long userSessionLifeTime) {
        this.userDao = userDao;
        this.userSessionLifeTime = userSessionLifeTime;
    }

    public Optional<UserSession> login(LoginRequest loginRequest) {
        return userDao
                .findUserByEmail(loginRequest.getEmail())
                .filter(foundUser -> isPasswordValid(loginRequest.getPassword(), foundUser))
                .map(this::register);
    }

    @Override
    public Optional<UserSession> findUserToken(String uuid) {
        return Optional.ofNullable(userSessionCache.getIfPresent(uuid));
    }

    public Optional<UserSession> logout(String uuid) {
        return findUserToken(uuid);
    }

    @PostConstruct
    void cacheInit() {
        this.userSessionCache = Caffeine.newBuilder()
                .expireAfterWrite(userSessionLifeTime, TimeUnit.MILLISECONDS)
                .build();
    }

    private UserSession register(User user) {
        var userTokenToBeReturned = getUserTokenIfExists(user.getNickname()).orElseGet(() -> new UserSession(UUID.randomUUID().toString(), user));
        var uuidKey = userTokenToBeReturned.getToken();
        if (!userSessionCache.asMap().containsKey(uuidKey)) {
            userSessionCache.put(uuidKey, userTokenToBeReturned);
        }
        return userTokenToBeReturned;
    }

    private Optional<UserSession> getUserTokenIfExists(String nickName) {
        return userSessionCache
                .asMap()
                .values()
                .stream()
                .filter(t -> t.getUser().getNickname().equals(nickName))
                .findFirst();
    }

    private boolean isPasswordValid(String password, User user) {
        return encoder.matches(password + user.getSalt(), user.getPassword());
    }
}
