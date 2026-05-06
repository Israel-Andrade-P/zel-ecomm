package com.zeldev.zel_e_comm.datatests.confirmationrepo;

import com.zeldev.zel_e_comm.config.AppConfig;
import com.zeldev.zel_e_comm.entity.ConfirmationEntity;
import com.zeldev.zel_e_comm.entity.CredentialEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.repository.ConfirmationRepository;
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
public class ConfirmationRepositoryTest {
    @Autowired
    private ConfirmationRepository confirmationRepository;
    @Autowired
    private UserRepository userRepository;

    private final String KEY = "454569";

    @BeforeEach
    void setup() {
        var user = UserEntity.builder()
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

        var confirmation = new ConfirmationEntity(user, KEY);

        userRepository.save(user);
        confirmationRepository.save(confirmation);
    }

    @Test
    void findByKeyTest() {
        var result = confirmationRepository.findByKey(KEY);

        assertThat(result).isNotEmpty().get().extracting(ConfirmationEntity::getConfKey).isEqualTo(KEY);
    }

    @Test
    void redPath() {
        var result = confirmationRepository.findByKey("random_af");

        assertThat(result).isEmpty();
    }
}
