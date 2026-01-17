package com.zeldev.zel_e_comm.domain;

import com.zeldev.zel_e_comm.entity.CredentialEntity;
import com.zeldev.zel_e_comm.model.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserSecurity implements UserDetails {
    private final User user;
    private final CredentialEntity credential;

    public UserSecurity(User user, CredentialEntity credential) {
        this.user = user;
        this.credential = credential;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public @Nullable String getPassword() {
        return credential.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
