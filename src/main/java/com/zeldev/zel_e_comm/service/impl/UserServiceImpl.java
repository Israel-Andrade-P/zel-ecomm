package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.UserDTO;
import com.zeldev.zel_e_comm.repository.LocationRepository;
import com.zeldev.zel_e_comm.repository.RoleRepository;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LocationRepository addressRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;


    @Override
    public UserDTO deleteUser(String email) {
        return null;
    }

    @Override
    public UserDTO findUser(String email) {
        return null;
    }
}
