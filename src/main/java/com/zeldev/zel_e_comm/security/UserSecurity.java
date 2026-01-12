package com.zeldev.zel_e_comm.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class UserSecurity implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String email;
    @JsonIgnore
    private String password;
    private Set<GrantedAuthority> roles;

    public UserSecurity(String email, String password, Set<GrantedAuthority> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserSecurity that = (UserSecurity) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, roles);
    }
}
