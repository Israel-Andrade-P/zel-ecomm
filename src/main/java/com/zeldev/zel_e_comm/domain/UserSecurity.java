package com.zeldev.zel_e_comm.domain;

import com.zeldev.zel_e_comm.enumeration.UserStatus;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

import static com.zeldev.zel_e_comm.constants.Constants.ROLE_PREFIX;

public record UserSecurity(
        Long id,
        String username,
        String email,
        String password,
        Set<String> roles,
        boolean enabled,
        boolean accountNonExpired,
        boolean accountNonLocked,
        UserStatus status,
        Integer tokenVersion) implements UserDetails {

    public UserSecurity {
        roles = Set.copyOf(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(ROLE_PREFIX + r)).toList();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
