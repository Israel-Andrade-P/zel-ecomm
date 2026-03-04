package com.zeldev.zel_e_comm.unittests.authservice;

import com.zeldev.zel_e_comm.entity.CredentialEntity;
import com.zeldev.zel_e_comm.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetCredentialByUserIdTest extends AuthServiceBaseTest{

    @Test
    @DisplayName(
            """
        GIVEN: user ID
        WHEN: getCredentialByUserId is called
        THEN: return credential entity
                """
    )
    void greenPath() {
        Long userId = 3L;
        CredentialEntity credential = new CredentialEntity();
        credential.setPassword("blue_cheese69");

        when(credentialRepository.findByUserId(userId)).thenReturn(Optional.of(credential));

        var returnedCredential = authService.getCredentialByUserId(userId);

        assertEquals(credential.getPassword(), returnedCredential.getPassword());
    }

    @Test
    @DisplayName(
            """
        GIVEN: user ID
        WHEN: getCredentialByUserId is called
        THEN: invalid ID provided
        AND: throws exception
                """
    )
    void redPath() {
        when(credentialRepository.findByUserId(20L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.getCredentialByUserId(20L));

        verify(credentialRepository, times(1)).findByUserId(20L);
    }
}
