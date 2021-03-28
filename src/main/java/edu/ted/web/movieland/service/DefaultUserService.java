package edu.ted.web.movieland.service;

import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.util.GeneralUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserDao userDao;

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
}
