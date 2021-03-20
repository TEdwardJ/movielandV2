package edu.ted.web.movieland.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.entity.UserToken;
import edu.ted.web.movieland.service.SecurityService;
import edu.ted.web.movieland.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class DefaultSecurityService implements SecurityService {

    private final long userSessionLifeTime;

    private Cache<String, UserToken> userSessionCache;

    private final UserDao userDao;

    public DefaultSecurityService(UserDao userDao, @Value("${user.session.cache.lifeInMilliSeconds:7200000}") long userSessionLifeTime) {
        this.userDao = userDao;
        this.userSessionLifeTime = userSessionLifeTime;
    }

    public Optional<UserToken> login(String email, String password) {
        return userDao
                .findUserByEmail(email)
                .filter(foundUser -> isPasswordValid(password, foundUser))
                .map(this::register);
    }

    @Override
    public Optional<UserToken> findUserToken(String uuid) {
        return Optional.ofNullable(userSessionCache.getIfPresent(uuid));
    }

    @Override
    public int addUser(User user) {
        if (!Optional.ofNullable(user.getPassword()).orElse("").isEmpty()) {
            if (Optional.ofNullable(user.getSole()).orElse("").isEmpty()) {
                user.setSole(GeneralUtils.generateString(10));
            }
            user.setPassword(GeneralUtils.getEncrypted(user.getPassword() + user.getSole()));
            return userDao.addUser(user);
        }
        return 0;
    }

    public UserToken logout(String uuid) {
        var userTokenEntry = findUserToken(uuid);
        return userTokenEntry.orElse(null);
    }

    @PostConstruct
    public void cacheInit() {
        this.userSessionCache = Caffeine.newBuilder()
                .expireAfterWrite(userSessionLifeTime, TimeUnit.MILLISECONDS)
                .build();
    }

    private UserToken register(User user) {
        var userTokenToBeReturned = getUserTokenIfExists(user.getNickname()).orElseGet(() -> new UserToken(user.getNickname(), user));
        var uuidKey = userTokenToBeReturned.getUuid().toString();
        if (!userSessionCache.asMap().containsKey(uuidKey)) {
            userSessionCache.put(uuidKey, userTokenToBeReturned);
        }
        return userTokenToBeReturned;
    }

    private Optional<UserToken> getUserTokenIfExists(String nickName) {
        return userSessionCache
                .asMap()
                .values()
                .stream()
                .filter(t -> t.getNickname().equals(nickName))
                .findFirst();
    }

    private boolean isPasswordValid(String password, User user) {
        return userDao.isPasswordValid(user.getEmail(), GeneralUtils.getEncrypted(password + user.getSole()));
    }
}
