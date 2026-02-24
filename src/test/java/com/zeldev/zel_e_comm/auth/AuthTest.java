package com.zeldev.zel_e_comm.auth;

import com.zeldev.zel_e_comm.dto.request.LocationDTO;
import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.entity.RoleEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.RoleType;
import com.zeldev.zel_e_comm.repository.*;
import com.zeldev.zel_e_comm.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//THIS IS A UNIT TEST
@ExtendWith(MockitoExtension.class)
public class AuthTest {

    @Mock
    UserRepository userRepository;
    @Mock
    CredentialRepository credentialRepository;
    @Mock
    ConfirmationRepository confirmationRepository;
    @Mock
    LocationRepository locationRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthServiceImpl authService;


    @Test
    @DisplayName(
            """
            GIVEN: UserRequest 
            WHEN: createUser is called
            THEN: creates userEntity
            AND:         
                    """
    )
    void greenTest() {
        //assumptions
        LocationDTO locationDTO = new LocationDTO("testLand", "testCity", "testSt", "test555");
        UserRequest userRequest = new UserRequest("zel", null, null, null, null, locationDTO);
        UserEntity userTest = new UserEntity();
        userTest.setUsername("zel");
        RoleEntity role = new RoleEntity(RoleType.USER);

        when(userRepository.save(any())).thenReturn(userTest);

        when(roleRepository.findByRoleName(RoleType.USER)).thenReturn(Optional.of(role));

        //method call
        UserResponse response = authService.createUser(userRequest);

        verify(userRepository, times(1)).save(any());

        //assertions
        Assertions.assertEquals(userRequest.username(), response.username());
    }
}
