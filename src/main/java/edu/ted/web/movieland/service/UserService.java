package edu.ted.web.movieland.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.entity.UserToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface UserService {

    UserToken authorize(String email, String password);

    UserToken logout(String uuid);

    Optional<UserToken> findUserToken(String uuid);

    int addUser(User user);

}
