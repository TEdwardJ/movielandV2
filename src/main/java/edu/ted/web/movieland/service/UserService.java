package edu.ted.web.movieland.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.UserToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface UserService {

    public UserToken authorize(String email, String password);

    public UserToken logout(String uuid);


}
