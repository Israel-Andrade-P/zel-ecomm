package com.zeldev.zel_e_comm.datatests.credentialrepo;

import com.zeldev.zel_e_comm.config.AppConfig;
import com.zeldev.zel_e_comm.entity.CredentialEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.repository.CredentialRepository;
import com.zeldev.zel_e_comm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static com.zeldev.zel_e_comm.enumeration.UserStatus.ACTIVE;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(AppConfig.class)
public class CredentialRepositoryTest {
    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setup() {
        user = UserEntity.builder()
                .username("username69")
                .email("fake@mail")
                .tokenVersion(0)
                .status(ACTIVE)
                .dob(LocalDate.of(1988, 11, 23))
                .accountNonExpired(true)
                .telephone("555")
                .loginAttempts(1)
                .lastLogin(now())
                .accountNonLocked(true)
                .enabled(true)
                .build();

        var credential = new CredentialEntity(user, "1234");

        userRepository.save(user);
        credentialRepository.save(credential);
    }

    @Test
    void findByUserId() {
        var result = credentialRepository.findByUserId(user.getId());

        assertThat(result).isNotEmpty().get().extracting(CredentialEntity::getPassword).isEqualTo("1234");
        assertThat(result.get().getUser().getUsername()).isEqualTo("username69");
    }

    @Test
    void redPath() {
        var result = credentialRepository.findByUserId(1000L);

        assertThat(result).isEmpty();
    }
}
