package com.zeldev.zel_e_comm.security;

import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AuthService authService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = authService.getUserByEmail(email);
        return new UserSecurity(user, authService.getCredentialByUserId(user.getId()));
    }
}
