package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.entity.RoleEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zeldev.zel_e_comm.enumeration.UserStatus.ACTIVE;

public class UserUtils {
    public static UserEntity buildUserEntity(UserRequest user, Set<RoleEntity> roles) {
        return UserEntity.builder()
                .username(user.username())
                .email(user.email())
                .telephone(user.telephone())
                .dob(user.dob())
                .lastLogin(LocalDateTime.now())
                .loginAttempts(0)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .enabled(false)
                .roles(roles)
                .status(ACTIVE)
                .tokenVersion(0)
                .build();
    }

    public static UserResponse toDTO(UserEntity entity) {
        return UserResponse.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .dob(entity.getDob())
                .telephone(entity.getTelephone())
                .build();
    }

    public static UserSecurity fromUserEntity(UserEntity userEntity, String password) {
        Set<String> roles = userEntity.getRoles().stream().map(r -> r.getRole().name()).collect(Collectors.toSet());
        return new UserSecurity(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                password,
                roles,
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isAccountNonLocked(),
                userEntity.getStatus(),
                userEntity.getTokenVersion()
        );
    }
}
