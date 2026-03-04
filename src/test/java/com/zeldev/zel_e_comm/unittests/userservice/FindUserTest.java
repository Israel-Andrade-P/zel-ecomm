package com.zeldev.zel_e_comm.unittests.userservice;

import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FindUserTest extends UserServiceBaseTest{

    @Test
    @DisplayName(
            """
            GIVEN: string username 
            WHEN: findUser is called
            THEN: user entity exists in db
            AND:  return a user response       
                    """
    )
    void greenPath() {
        String username = "stanley69";
        UserEntity user = new UserEntity();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserResponse response = userService.findUser(username);

        assertNotNull(response);
        assertEquals(username, response.username());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName(
            """
            GIVEN: string username 
            WHEN: findUser is called
            THEN: user entity doesn't exist in db
            AND:  throws exception     
                    """
    )
    void redPath() {
        String username = "stanley69";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUser(username));

        verify(userRepository, times(1)).findByUsername(username);
    }
}
