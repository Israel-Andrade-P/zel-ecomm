package com.zeldev.zel_e_comm.unittests.jwtservice;

import com.zeldev.zel_e_comm.service.AuthService;
import com.zeldev.zel_e_comm.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtServiceBaseTest {
    @Mock protected AuthService authService;

    @InjectMocks protected JwtServiceImpl jwtService;
}
