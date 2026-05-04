package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.UserStatus;
import com.zeldev.zel_e_comm.exception.UserNotFoundException;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.JwtService;
import com.zeldev.zel_e_comm.service.UserService;
import com.zeldev.zel_e_comm.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zeldev.zel_e_comm.util.UserUtils.toDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public UserResponse findUser(String username) {
        return toDTO(getUserByUsername(username));
    }

    @Override
    public UserResponse updateUser(UserRequest request, String username) {
        UserEntity user = getUserByUsername(username);

        if (request.username() != null && !request.username().isBlank()) user.setUsername(request.username());
        if (request.email() != null && !request.email().isBlank()) user.setEmail(request.email());
        if (request.telephone() != null && !request.telephone().isBlank()) user.setTelephone(request.telephone());

        return toDTO(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserUtils::toDTO).toList();
    }

    @Override
    public String deleteUser(String username) {
        UserEntity user = getUserByUsername(username);

        if (user.getStatus() == UserStatus.DELETED) return "User already deleted";

        user.setStatus(UserStatus.DELETED);
        user.setTokenVersion(user.getTokenVersion() + 1);
        user.setEnabled(false);
        user.setAccountNonLocked(false);

        return "User deleted";
    }

    private UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
    }
}
