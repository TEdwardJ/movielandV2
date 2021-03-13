package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {
    private final String email = "xxx@gmail.com";
    private final String password = "password";

    @Mock
    private UserDao dao;

    private DefaultUserService service;

    @BeforeEach
    public void init() {
        service = new DefaultUserService(dao, 7200000);
        service.cacheInit();
        User user = new User();
        user.setEmail(email);
        user.setSole("justSole");
        user.setId(12);
        user.setPassword(service.getEncrypted(password, user.getSole()));
        user.setNickname("TomCat");

        when(dao.findUserByEmail(email)).thenReturn(user);
        when(dao.isPasswordValid(any(), any())).thenReturn(true);
    }

    @Test
    void givenEmailAndPasswordOfExistingUser_whenTokenIsReturned_thenCorrect() {
        var userToken = service.authorize(email, password);
        assertEquals("TomCat", userToken.getNickname());
        assertNotNull("TomCat", userToken.getUuid().toString());
    }

    @Test
    void givenEmailAndPasswordOfLoggedUser_whenTheSameTokenIsReturned_thenCorrect() {
        var userToken = service.authorize(email, password);
        assertEquals("TomCat", userToken.getNickname());
        assertNotNull("TomCat", userToken.getUuid().toString());
        var tokenForAlreadyLoggedInUser = service.authorize(email, password);
        assertEquals(userToken.getUuid(), tokenForAlreadyLoggedInUser.getUuid());
    }

    @Test
    void givenUserHasLoggedInAndHasToken_thenTriesLogout_whenTokenReturnedAndEquals_thenCorrect() {
        var userToken = service.authorize(email, password);
        assertEquals("TomCat", userToken.getNickname());
        assertNotNull("TomCat", userToken.getUuid().toString());
        var logoutResult = service.logout(userToken.getUuid().toString());
        assertEquals(userToken.getUuid(), logoutResult.getUuid());
    }
}