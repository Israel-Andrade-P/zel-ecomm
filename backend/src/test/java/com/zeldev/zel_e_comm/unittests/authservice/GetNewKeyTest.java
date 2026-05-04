package com.zeldev.zel_e_comm.unittests.authservice;

import com.zeldev.zel_e_comm.dto.request.KeyRequest;
import com.zeldev.zel_e_comm.entity.ConfirmationEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetNewKeyTest extends AuthServiceBaseTest{
    UserEntity user;
    KeyRequest keyRequest;

    @BeforeEach
    void beforeEach() {
        user = new UserEntity();
        keyRequest = new KeyRequest("zel@gmail");
    }

    @Test
    @DisplayName(
            """
        GIVEN: keyRequest obj
        WHEN: getNewKey is called
        THEN: create new confirmation entity
        AND:  return a message to user
                """
    )
    void greenPath() {
        user.setEmail(keyRequest.email());

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        String message = authService.getNewKey(keyRequest);

        assertEquals("New key generated!", message);

        ArgumentCaptor<ConfirmationEntity> captor = ArgumentCaptor.forClass(ConfirmationEntity.class);

        verify(confirmationRepository, times(1)).save(captor.capture());

        ConfirmationEntity saved = captor.getValue();

        assertEquals(user, saved.getUser());
        assertNotNull(saved.getConfKey());
        assertEquals(6, saved.getConfKey().length());
    }

    @Test
    @DisplayName(
            """
        GIVEN: keyRequest obj
        WHEN: getNewKey is called with invalid email
        THEN: throw exception
                """
    )
    void invalidEmail() {
        when(userRepository.findByEmail(keyRequest.email())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authService.getNewKey(keyRequest));

        verify(confirmationRepository, never()).save(any());
    }
}
