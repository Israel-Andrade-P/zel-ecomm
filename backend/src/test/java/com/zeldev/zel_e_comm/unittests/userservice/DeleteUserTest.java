package com.zeldev.zel_e_comm.unittests.userservice;

import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.zeldev.zel_e_comm.enumeration.UserStatus.ACTIVE;
import static com.zeldev.zel_e_comm.enumeration.UserStatus.DELETED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeleteUserTest extends UserServiceBaseTest{
    private static final String USERNAME = "zel999";

    @Test
    @DisplayName(
            """
            GIVEN: a string username 
            WHEN: deleteUser is called
            THEN: soft delete user 
                    """
    )
    void greenPath() {
        UserEntity user = new UserEntity();
        user.setUsername("karl_jones");
        user.setEmail("karl@gmail");
        user.setStatus(ACTIVE);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setTokenVersion(0);

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        String message = userService.deleteUser(USERNAME);

        assertEquals(DELETED, user.getStatus());
        assertFalse(user.isEnabled());
        assertFalse(user.isAccountNonLocked());
        assertEquals(1, user.getTokenVersion());
        assertEquals("User deleted", message);

        verify(userRepository, times(1)).findByUsername(USERNAME);
    }

    @Test
    @DisplayName(
            """
            GIVEN: a string username 
            WHEN: deleteUser is called
            THEN: user already with status DELETED 
                    """
    )
    void alreadyDeleted() {
        UserEntity user = new UserEntity();
        user.setStatus(DELETED);
        user.setEnabled(false);
        user.setAccountNonLocked(false);
        user.setTokenVersion(2);
        var originalVersion = user.getTokenVersion();

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        String message = userService.deleteUser(USERNAME);

        assertEquals(DELETED, user.getStatus());
        assertEquals("User already deleted", message);
        assertEquals(originalVersion, user.getTokenVersion());

        verify(userRepository, times(1)).findByUsername(USERNAME);
    }

    @Test
    @DisplayName(
            """
            GIVEN: a string username 
            WHEN: deleteUser is called
            THEN: invalid username
            AND: throws exception
                    """
    )
    void redPath() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(USERNAME));

        verify(userRepository, times(1)).findByUsername(USERNAME);
    }
}
