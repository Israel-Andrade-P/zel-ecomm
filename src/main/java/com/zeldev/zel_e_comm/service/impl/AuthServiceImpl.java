package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.cache.CacheStore;
import com.zeldev.zel_e_comm.dto.dto_class.UserDTO;
import com.zeldev.zel_e_comm.entity.ConfirmationEntity;
import com.zeldev.zel_e_comm.entity.CredentialEntity;
import com.zeldev.zel_e_comm.entity.RoleEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.LoginType;
import com.zeldev.zel_e_comm.enumeration.RoleType;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.exception.RoleDoesntExistException;
import com.zeldev.zel_e_comm.repository.*;
import com.zeldev.zel_e_comm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static com.zeldev.zel_e_comm.util.LocationUtils.buildLocation;
import static com.zeldev.zel_e_comm.util.UserUtils.buildUserEntity;
import static com.zeldev.zel_e_comm.util.UserUtils.toDTO;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final ConfirmationRepository confirmationRepository;
    private final LocationRepository locationRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final CacheStore<String, Integer> userCache;

    @Override
    public UserDTO createUser(UserDTO user) {
        UserEntity userEntity = userRepository.save(createNewUser(user));
        credentialRepository.save(new CredentialEntity(userEntity, encoder.encode(user.password())));
        ConfirmationEntity confirmation = confirmationRepository.save(new ConfirmationEntity(userEntity, suppliesKey.get()));
        locationRepository.save(buildLocation(user.location(), userEntity));
        return toDTO(userEntity);
    }

    @Override
    public void verifyAccount(String key) {

    }

    @Override
    public void updateLoginAttempt(String email, LoginType loginType) {
        var user = getUserEntityByEmail(email);
        switch (loginType){
            case LOGIN_ATTEMPT -> {
                if (userCache.get(user.getEmail()) == null){
                    user.setLoginAttempts(0);
                    user.setAccountNonLocked(true);
                }
                user.setLoginAttempts(user.getLoginAttempts() + 1);
                userCache.put(user.getEmail(), user.getLoginAttempts());
                if (userCache.get(user.getEmail()) > 5){
                    user.setAccountNonLocked(false);
                }
            }
            case LOGIN_SUCCESS -> {
                user.setLoginAttempts(0);
                user.setAccountNonLocked(true);
                user.setLastLogin(now());
                userCache.evict(user.getEmail());
            }
        }
        userRepository.save(user);
    }

    private UserEntity getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(email, "User"));
    }

    @Override
    public CredentialEntity getCredentialByUserId(Long userId) {
        return null;
    }

    private UserEntity createNewUser(UserDTO user) {
        var roles = getRoles();
        return buildUserEntity(user, roles);
    }

    private Set<RoleEntity> getRoles() {
        var role = roleRepository.findByRoleName(RoleType.USER)
                .orElseThrow(() -> new RoleDoesntExistException("This role doesn't exist"));
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }

    private final Supplier<String> suppliesKey = () -> {
        String pool = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++){
            int randomIndex = random.nextInt(pool.length());
            builder.append(pool.charAt(randomIndex));
        }
        return builder.toString();
    };
}
