package com.zeldev.zel_e_comm.unittests.userservice;

import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.JwtService;
import com.zeldev.zel_e_comm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class UserServiceBaseTest {
    @Mock protected UserRepository userRepository;
    @Mock protected JwtService jwtService;

    @InjectMocks protected UserServiceImpl userService;
}
