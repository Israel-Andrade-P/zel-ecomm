package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.dto.dto_class.UserDTO;
import com.zeldev.zel_e_comm.entity.RoleEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUtils {
    public static UserEntity buildUserEntity(UserDTO user, Set<RoleEntity> roles) {
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

    public static User fromUserEntity(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .dob(userEntity.getDob())
                .lastLogin(userEntity.getLastLogin())
                .loginAttempts(userEntity.getLoginAttempts())
                .accountNonLocked(userEntity.isAccountNonLocked())
                .accountNonExpired(userEntity.isAccountNonExpired())
                .enabled(userEntity.isEnabled())
                .roles(userEntity.getRoles().stream().map(r -> r.getRole().name()).collect(Collectors.toSet()))
                .build();
    }

    public static User fromUserSecurity(UserSecurity userSecurity) {
        return User.builder()
                .email(userSecurity.getUsername())
                .roles(userSecurity.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();

    }
}
