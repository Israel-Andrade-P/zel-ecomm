package com.zeldev.zel_e_comm.unittests.authservice;

import com.zeldev.zel_e_comm.entity.ConfirmationEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.exception.ConfirmationKeyExpiredException;
import com.zeldev.zel_e_comm.exception.CustomInvalidKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class VerifyAccountTest extends AuthServiceBaseTest {
    ConfirmationEntity confirmationEntity;
    UserEntity user;
    String key;

    @BeforeEach
    void beforeEach() {
        user = new UserEntity();
        user.setEnabled(false);
       confirmationEntity = new ConfirmationEntity();
       confirmationEntity.setConfKey("myKey");
       confirmationEntity.setCreatedAt(LocalDateTime.now());
       confirmationEntity.setUser(user);
       key = "myKey";
    }

    @Test
    @DisplayName(
            """
            GIVEN: String key
            WHEN: verifyAccount is called
            THEN: set user enabled status to true
            AND:  delete confirmation entity
                    """
    )
    void greenTest() {
        user.setEmail("user@test.com");

        when(confirmationRepository.findByKey(key)).thenReturn(Optional.of(confirmationEntity));

        authService.verifyAccount(key);

        assertTrue(user.isEnabled());

        verify(confirmationRepository, times(1)).findByKey(key);
        verify(confirmationRepository,times(1)).delete(confirmationEntity);
    }

    @Test
    @DisplayName(
            """
         GIVEN: String key
         WHEN: verifyAccount is called
         THEN: provide expired key
         AND:  throws exception
                 """
    )
    void invalidKey() {
        when(confirmationRepository.findByKey(key)).thenReturn(Optional.empty());

        assertThrows(CustomInvalidKeyException.class, () -> authService.verifyAccount(key));

        verify(confirmationRepository, times(1)).findByKey(key);
    }

    @Test
    @DisplayName(
            """
          GIVEN: String key
          WHEN: verifyAccount is called
          THEN: provide expired key
          AND:  throws exception
                  """
    )
    void expiredKey() {
        confirmationEntity.setCreatedAt(LocalDateTime.now().minusMinutes(35));

        when(confirmationRepository.findByKey(key)).thenReturn(Optional.of(confirmationEntity));

        assertThrows(ConfirmationKeyExpiredException.class, () -> authService.verifyAccount(key));
        verify(confirmationRepository, times(1)).delete(confirmationEntity);
    }
}
