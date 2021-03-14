package edu.ted.web.movieland.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.entity.UserToken;
import edu.ted.web.movieland.service.SecurityService;
import edu.ted.web.movieland.util.GeneralUtils;
import org.apache.commons.codec.digest.DigestUtils;
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

    public UserToken authorize(String email, String password) {
        var user = userDao.findUserByEmail(email);
        if (user != null) {
            var inputEncPassword = getEncrypted(password + user.getSole());
            //if (inputEncPassword.equals(user.getPassword())) {
            if (userDao.isPasswordValid(user.getEmail(), inputEncPassword)) {
                return register(user);
            }
        }
        return null;
    }

    private UserToken register(User user) {
        var userTokenToBeReturned = getUserTokenIfExists(user.getNickname()).orElseGet(() -> new UserToken(user.getNickname()));
        var keyUUID = userTokenToBeReturned.getUuid().toString();
        if (!userSessionCache.asMap().containsKey(keyUUID)) {
            userSessionCache.put(keyUUID, userTokenToBeReturned);
        }
        userTokenToBeReturned.setUser(user);
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

    @Override
    public Optional<UserToken> findUserToken(String uuid) {
        return Optional.ofNullable(userSessionCache.get(uuid, k -> null));
    }

    @Override
    public int addUser(User user) {
        if (!Optional.ofNullable(user.getPassword()).orElse("").isEmpty()) {
            if (Optional.ofNullable(user.getSole()).orElse("").isEmpty()) {
                user.setSole(GeneralUtils.generateString(10));
            }
            user.setPassword(getEncrypted(user.getPassword() + user.getSole()));
            return userDao.addUser(user);
        }
        return 0;
    }

    public UserToken logout(String uuid) {
        var userTokenEntry = findUserToken(uuid);
        return userTokenEntry.orElse(null);
    }

    public static String getEncrypted(String text) {
        return DigestUtils.md5Hex(text);
    }

    @PostConstruct
    public void cacheInit() {
        this.userSessionCache = Caffeine.newBuilder()
                .expireAfterWrite(userSessionLifeTime, TimeUnit.MILLISECONDS)
                .build();
    }
}
