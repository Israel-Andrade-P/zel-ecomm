package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.UserDTO;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.repository.LocationRepository;
import com.zeldev.zel_e_comm.repository.RoleRepository;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LocationRepository addressRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public UserDTO registerUser(UserDTO request) {
        UserEntity userEntity = mapper.map(request, UserEntity.class);
        userEntity.getRoles().add(roleRepository.findById(1L).get());
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        addressRepository.save(LocationEntity.builder()
                .street(request.getStreet())
                .city(request.getCity())
                .country(request.getCountry())
                .zipCode(request.getZipCode())
                .user(userEntity)
                .build());

        return mapper.map(userEntity, UserDTO.class);
    }

    @Override
    public UserDTO deleteUser(String email) {
        return null;
    }

    @Override
    public UserDTO findUser(String email) {
        return null;
    }
}
