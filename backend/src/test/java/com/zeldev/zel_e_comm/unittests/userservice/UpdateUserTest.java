package com.zeldev.zel_e_comm.unittests.userservice;

import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateUserTest extends UserServiceBaseTest {
    private static final String USERNAME = "zel999";

    @Test
    @DisplayName(
            """
            GIVEN: userRequest and string username 
            WHEN: updateUser is called
            THEN: update user with provided info
            AND:  return a user response       
                    """
    )
    void greenPath() {
        var userEntity = createEntity();
        var request = new UserRequest("zel999", "zel@gmail", "secret", "555", null, null);

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userEntity));

        UserResponse response = userService.updateUser(request, USERNAME);

        assertEquals(request.username(), response.username());
        assertEquals(request.email(), response.email());
        assertEquals(request.telephone(), response.telephone());

        verify(userRepository, times(1)).findByUsername(USERNAME);
    }

    @Test
    @DisplayName(
            """
            GIVEN: userRequest and string username 
            WHEN: updateUser is called
            THEN: only update info present in request
            AND:  return a user response       
                    """
    )
    void greenPath2() {
        var userEntity = createEntity();
        var request = new UserRequest("zel999", "", "secret", null, null, null);

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userEntity));

        UserResponse response = userService.updateUser(request, USERNAME);

        assertEquals(request.username(), response.username());
        assertEquals(userEntity.getEmail(), response.email());
        assertEquals(userEntity.getTelephone(), response.telephone());
        assertNotNull(response.telephone());

        verify(userRepository, times(1)).findByUsername(USERNAME);
    }

    @Test
    @DisplayName(
            """
            GIVEN: userRequest and string username 
            WHEN: updateUser is called
            THEN: invalid username
            AND:  throws exception   
                    """
    )
    void redPath() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(null, USERNAME));

        verify(userRepository, times(1)).findByUsername(USERNAME);
    }

    private UserEntity createEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("karl_jones");
        userEntity.setEmail("karl@gmail");
        userEntity.setTelephone("9000");
        return userEntity;
    }
}
