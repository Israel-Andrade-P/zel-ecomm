package com.zeldev.zel_e_comm.unittests.userservice;

import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class GetAllUsersTest extends  UserServiceBaseTest{

    @Test
    @DisplayName(
            """ 
            WHEN: getAllUsers is called
            THEN: return a list of userResponses 
                    """
    )
    void greenPath() {
        UserEntity user1 = new UserEntity();
        user1.setUsername("karen");
        UserEntity user2 = new UserEntity();
        user2.setUsername("miles");
        List<UserEntity> users = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserResponse> responses = userService.getAllUsers();

        assertEquals(2, responses.size());
        assertEquals("miles", responses.get(1).username());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName(
            """ 
            WHEN: getAllUsers is called
            THEN: return a empty list 
                    """
    )
    void empty() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<UserResponse> responses = userService.getAllUsers();

        assertTrue(responses.isEmpty());

        verify(userRepository, times(1)).findAll();
    }
}
