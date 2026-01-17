package com.zeldev.zel_e_comm.security;

import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.entity.CredentialEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.repository.CredentialRepository;
import com.zeldev.zel_e_comm.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.zeldev.zel_e_comm.util.UserUtils.fromUserEntity;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userDB = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        CredentialEntity credential = credentialRepository.findByUserId(userDB.getId()).orElseThrow(() -> new UsernameNotFoundException("credential not found"));
        return new UserSecurity(fromUserEntity(userDB), credential);
    }
}
