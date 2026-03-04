package com.zeldev.zel_e_comm.unittests.authservice;

import com.zeldev.zel_e_comm.cache.CacheStore;
import com.zeldev.zel_e_comm.repository.*;
import com.zeldev.zel_e_comm.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public abstract class AuthServiceBaseTest {
    @Mock protected UserRepository userRepository;
    @Mock protected CredentialRepository credentialRepository;
    @Mock protected ConfirmationRepository confirmationRepository;
    @Mock protected LocationRepository locationRepository;
    @Mock protected RoleRepository roleRepository;
    @Mock protected PasswordEncoder passwordEncoder;
    @Mock protected CacheStore<String, Integer> userCache;

    @InjectMocks AuthServiceImpl authService;
}
