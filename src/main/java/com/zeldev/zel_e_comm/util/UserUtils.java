package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.dto_class.UserDTO;
import com.zeldev.zel_e_comm.entity.RoleEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Set;

public class UserUtils {
    public static UserEntity buildUserEntity(UserDTO user, Set<RoleEntity> roles){
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
                .build();
    }

    public static UserDTO toDTO(UserEntity entity) {
        return UserDTO.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .dob(entity.getDob())
                .telephone(entity.getTelephone())
                .build();
    }
}
