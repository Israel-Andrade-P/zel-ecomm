package com.zeldev.zel_e_comm.security;

import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userDB = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Set<GrantedAuthority> authorities = userDB.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole().name())).collect(Collectors.toSet());
        return new UserSecurity(userDB.getEmail(), userDB.getPassword(), authorities);
    }
}
