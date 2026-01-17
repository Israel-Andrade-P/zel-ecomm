package com.zeldev.zel_e_comm.model;

import com.zeldev.zel_e_comm.entity.RoleEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private String email;
    private LocalDate dob;
    private Integer loginAttempts;
    private LocalDateTime lastLogin;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean enabled;
    private Set<String> roles;
}

