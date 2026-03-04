package com.zeldev.zel_e_comm.unittests.authservice;

import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.LoginType;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateLoginAttemptTest extends AuthServiceBaseTest{
    UserEntity user;

    @BeforeEach
    void beforeEach() {
        user = new UserEntity();
        user.setAccountNonLocked(true);
    }

    @Test
    @DisplayName(
            """
         GIVEN: email and login type LOGIN_ATTEMPT
         WHEN: updateLoginAttempt is called
         THEN: increment login attempts
         AND:  update user
                 """
    )
    void firstAttempt() {
        String email ="user@test.com";
        user.setEmail(email);
        user.setLoginAttempts(0);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userCache.get(user.getEmail())).thenReturn(null);

        authService.updateLoginAttempt(email, LoginType.LOGIN_ATTEMPT);

        assertEquals(1, user.getLoginAttempts());
        assertTrue(user.isAccountNonLocked());
        verify(userRepository, times(1)).save(user);
        verify(userCache, times(1)).put(user.getEmail(), user.getLoginAttempts());
    }

    @Test
    @DisplayName(
            """
         GIVEN: email and login type LOGIN_ATTEMPT
         WHEN: updateLoginAttempt is called
         THEN: increment login attempts
         AND:  update user
                 """
    )
    void incrementAttempt() {
        String email ="user@test.com";
        user.setEmail(email);
        user.setLoginAttempts(2);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userCache.get(user.getEmail())).thenReturn(user.getLoginAttempts());

        authService.updateLoginAttempt(email, LoginType.LOGIN_ATTEMPT);

        assertEquals(3, user.getLoginAttempts());
        assertTrue(user.isAccountNonLocked());
        verify(userRepository, times(1)).save(user);
        verify(userCache, times(1)).put(user.getEmail(), user.getLoginAttempts());
    }

    @Test
    @DisplayName(
            """
         GIVEN: email and login type LOGIN_ATTEMPT
         WHEN: updateLoginAttempt is called
         THEN: login attempts equal 5
         AND:  lock user's account
                 """
    )
    void lockAccount() {
        String email ="user@test.com";
        Integer loginAttempt = 5;
        user.setEmail(email);
        user.setLoginAttempts(loginAttempt);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userCache.get(user.getEmail())).thenReturn(user.getLoginAttempts());

        authService.updateLoginAttempt(email, LoginType.LOGIN_ATTEMPT);

        assertEquals(6, user.getLoginAttempts());
        assertFalse(user.isAccountNonLocked());
        verify(userRepository, times(1)).save(user);
        verify(userCache, times(1)).put(user.getEmail(), user.getLoginAttempts());
    }

    @Test
    @DisplayName(
            """
         GIVEN: email and login type LOGIN_SUCCESS
         WHEN: updateLoginAttempt is called
         THEN: login attempts equal 3
         AND:  let user in
                 """
    )
    void loginSuccess() {
        String email ="user@test.com";
        Integer loginAttempt = 3;
        user.setEmail(email);
        user.setLoginAttempts(loginAttempt);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        authService.updateLoginAttempt(email, LoginType.LOGIN_SUCCESS);

        assertEquals(0, user.getLoginAttempts());
        assertTrue(user.isAccountNonLocked());
        verify(userRepository, times(1)).save(user);
        verify(userCache, times(1)).evict(user.getEmail());
    }

    @Test
    @DisplayName(
            """
           GIVEN: email
           WHEN: updateLoginAttempt is called
           THEN: call user repo findByEmail
           AND:  throws exception
                   """
    )
    void invalidEmail() {
        String fakeEmail = "fake@test.com";
        LoginType loginType = LoginType.LOGIN_ATTEMPT;
        when(userRepository.findByEmail(fakeEmail)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authService.updateLoginAttempt(fakeEmail, loginType));

        verifyNoMoreInteractions(userCache);
    }
}
