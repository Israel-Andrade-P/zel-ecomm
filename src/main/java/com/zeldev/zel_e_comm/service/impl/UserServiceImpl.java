package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.exception.UserNotFoundException;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zeldev.zel_e_comm.util.UserUtils.toDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserResponse findUser(String username) {
        return toDTO(getUserByUsername(username));
    }

    @Override
    public String deleteUser(String username) {
        int deleted = userRepository.deleteByUsername(username);
        if (deleted == 0) throw new UserNotFoundException(String.format("User with username %s not found", username));

        return "User deleted";
    }

    private UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
    }
}
