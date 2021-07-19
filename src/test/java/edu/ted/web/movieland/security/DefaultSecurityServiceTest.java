package edu.ted.web.movieland.security;

import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.request.LoginRequest;
import edu.ted.web.movieland.util.GeneralUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultSecurityServiceTest {
    private final String email = "xxx@gmail.com";
    private final String password = "password";

    @Mock
    private UserDao dao;

    private DefaultSecurityService service;

    @BeforeAll
    public static void initAll(){
        GeneralUtils.setPasswordEncoder(new BCryptPasswordEncoder());
    }

    @BeforeEach
    public void init() {
        service = new DefaultSecurityService(dao, 7200000);
        service.cacheInit();
        User user = new User();
        user.setEmail(email);
        user.setSalt("justSole");
        user.setId(12);
        user.setPassword(GeneralUtils.getEncrypted(password + user.getSalt()));
        user.setNickname("TomCat");

        when(dao.findUserByEmail(email)).thenReturn(Optional.of(user));
    }

    @Test
    void givenEmailAndPasswordOfExistingUser_whenTokenIsReturned_thenCorrect() {
        var userSession = service.login(new LoginRequest(email, password)).get();
        assertEquals("TomCat", userSession.getUser().getNickname());
        assertNotNull("TomCat", userSession.getToken());
    }

    @Test
    void givenEmailAndPasswordOfLoggedUser_whenTheSameTokenIsReturned_thenCorrect() {
        var userToken = service.login(new LoginRequest(email, password)).get();
        assertEquals("TomCat", userToken.getUser().getNickname());
        assertNotNull("TomCat", userToken.getToken());
        var tokenForAlreadyLoggedInUser = service.login(new LoginRequest(email, password));
        assertEquals(userToken.getToken(), tokenForAlreadyLoggedInUser.get().getToken());
    }

    @Test
    void givenUserHasLoggedInAndHasToken_thenTriesLogout_whenTokenReturnedAndEquals_thenCorrect() {
        var userToken = service.login(new LoginRequest(email, password)).get();
        assertEquals("TomCat", userToken.getUser().getNickname());
        assertNotNull("TomCat", userToken.getToken());
        var logoutResult = service.logout(userToken.getToken());
        assertEquals(userToken.getToken(), logoutResult.get().getToken());
    }
}