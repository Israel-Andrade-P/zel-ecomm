package com.zeldev.zel_e_comm.unittests.authservice;

import com.zeldev.zel_e_comm.dto.request.LocationDTO;
import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.entity.RoleEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.RoleType;
import com.zeldev.zel_e_comm.exception.RoleDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateUserTest extends AuthServiceBaseTest{
    LocationDTO locationDTO;
    UserRequest userRequest;
    UserEntity userTest;
    RoleEntity role;

    @BeforeEach
    void beforeEach() {
        locationDTO = new LocationDTO("testLand", "testCity", "testSt", "test555");
        userRequest = new UserRequest("zel", null, null, null, null, locationDTO);
        userTest = new UserEntity();
        userTest.setUsername("zel");
        role = new RoleEntity(RoleType.USER);
    }

    @Test
    @DisplayName(
            """
            GIVEN: UserRequest 
            WHEN: createUser is called
            THEN: Role does not exist
            AND:  Throws exception       
                    """
    )
    void greenTest() {
        //assumptions
        stubHappyPath();

        //method call
        UserResponse response = authService.createUser(userRequest);

        verify(userRepository, times(1)).save(any());

        //assertions
        assertEquals(userRequest.username(), response.username());
    }

    @Test
    @DisplayName(
            """
           GIVEN: UserRequest 
           WHEN: createUser is called
           THEN: Encode password from request
           AND:  Call credentialRepo save method     
                   """
    )
    void verifyEncoding() {
        stubHappyPath();

        authService.createUser(userRequest);

        verify(passwordEncoder, times(1)).encode(userRequest.password());
        verify(credentialRepository, times(1)).save(any());
    }

    @Test
    @DisplayName(
            """
           GIVEN: UserRequest 
           WHEN: createUser is called
           THEN: Call confirmationRepo save method 
                   """
    )
    void verifyConfirmationCreation() {
        stubHappyPath();

        authService.createUser(userRequest);

        verify(confirmationRepository, times(1)).save(any());
    }

    @Test
    @DisplayName(
            """
           GIVEN: UserRequest 
           WHEN: createUser is called
           THEN: Call locationRepo save method 
                   """
    )
    void verifyLocationCreation() {
        stubHappyPath();

        authService.createUser(userRequest);

        verify(locationRepository, times(1)).save(any());
    }

    @Test
    @DisplayName(
            """
           GIVEN: UserRequest 
           WHEN: createUser is called
           THEN: Call encoder.encoded method
           AND: Throws exception
                   """
    )
    void throwPasswordEncoderException() {
        when(roleRepository.findByRoleName(RoleType.USER))
                .thenReturn(Optional.of(role));

        when(userRepository.save(any()))
                .thenReturn(userTest);

        when(passwordEncoder.encode(any())).thenThrow(new RuntimeException("encoding failed"));

        assertThrows(RuntimeException.class, () -> authService.createUser(userRequest));

        verify(credentialRepository, never()).save(any());
    }

    @Test
    @DisplayName(
            """
           GIVEN: UserRequest 
           WHEN: createUser is called
           THEN: Call userRepo.save method
           AND: Throws exception
                   """
    )
    void throwUserRepoException() {
        when(roleRepository.findByRoleName(RoleType.USER)).thenReturn(Optional.of(role));
        when(userRepository.save(any())).thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () -> authService.createUser(userRequest));

        verify(passwordEncoder, never()).encode(any());
        verify(credentialRepository, never()).save(any());
        verify(confirmationRepository, never()).save(any());
        verify(locationRepository, never()).save(any());
    }

    @Test
    @DisplayName(
            """
            GIVEN: UserRequest 
            WHEN: createUser is called
            THEN: pass in invalid Role
            AND:  throws RoleDoesntExistException       
                    """
    )
    void redTest() {
        when(roleRepository.findByRoleName(RoleType.USER)).thenReturn(Optional.empty());

        assertThrows(RoleDoesntExistException.class, () -> authService.createUser(userRequest));

        verify(userRepository, never()).save(any());
    }

    private void stubHappyPath() {
        when(roleRepository.findByRoleName(RoleType.USER))
                .thenReturn(Optional.of(role));

        when(userRepository.save(any()))
                .thenReturn(userTest);

        when(passwordEncoder.encode(any()))
                .thenReturn("encoded");
    }
}
