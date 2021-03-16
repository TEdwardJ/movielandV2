package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.util.GeneralUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @BeforeEach
    public void init() {
        service = new DefaultSecurityService(dao, 7200000);
        service.cacheInit();
        User user = new User();
        user.setEmail(email);
        user.setSole("justSole");
        user.setId(12);
        user.setPassword(GeneralUtils.getEncrypted(password + user.getSole()));
        user.setNickname("TomCat");

        when(dao.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(dao.isPasswordValid(any(), any())).thenReturn(true);
    }

    @Test
    void givenEmailAndPasswordOfExistingUser_whenTokenIsReturned_thenCorrect() {
        var userToken = service.login(email, password).get();
        assertEquals("TomCat", userToken.getNickname());
        assertNotNull("TomCat", userToken.getUuid().toString());
    }

    @Test
    void givenEmailAndPasswordOfLoggedUser_whenTheSameTokenIsReturned_thenCorrect() {
        var userToken = service.login(email, password).get();
        assertEquals("TomCat", userToken.getNickname());
        assertNotNull("TomCat", userToken.getUuid().toString());
        var tokenForAlreadyLoggedInUser = service.login(email, password);
        assertEquals(userToken.getUuid(), tokenForAlreadyLoggedInUser.get().getUuid());
    }

    @Test
    void givenUserHasLoggedInAndHasToken_thenTriesLogout_whenTokenReturnedAndEquals_thenCorrect() {
        var userToken = service.login(email, password).get();
        assertEquals("TomCat", userToken.getNickname());
        assertNotNull("TomCat", userToken.getUuid().toString());
        var logoutResult = service.logout(userToken.getUuid().toString());
        assertEquals(userToken.getUuid(), logoutResult.getUuid());
    }
}