package com.zeldev.zel_e_comm.datatests.userrepo;

import com.zeldev.zel_e_comm.config.AppConfig;
import com.zeldev.zel_e_comm.entity.RoleEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.RoleType;
import com.zeldev.zel_e_comm.repository.RoleRepository;
import com.zeldev.zel_e_comm.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Set;

import static com.zeldev.zel_e_comm.enumeration.UserStatus.ACTIVE;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(AppConfig.class)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private static UserEntity createUser(String username, String email) {
        RoleEntity role = new RoleEntity(RoleType.USER);
        return UserEntity.builder()
                .username(username)
                .email(email)
                .tokenVersion(0)
                .status(ACTIVE)
                .dob(LocalDate.of(1988, 11, 23))
                .accountNonExpired(true)
                .roles(Set.of(role))
                .telephone("555")
                .loginAttempts(1)
                .lastLogin(now())
                .accountNonLocked(true)
                .enabled(true)
                .build();
    }

    @BeforeEach
    void prepopulate() {
        var user = createUser("claude69", "claude@gmail");
        roleRepository.saveAll(user.getRoles());
        userRepository.save(user);
    }

    @Test
    void findByEmailTest() {
        assertThat(userRepository.findByEmail("claude@gmail"))
                .isNotEmpty()
                .get()
                .extracting(UserEntity::getUsername)
                .isEqualTo("claude69");
    }

    @Test
    void userNotFoundTest() {
        assertThat(userRepository.findByEmail("fake@gmail")).isEmpty();
    }

    @Test
    void findByUsernameTest() {
        assertThat(userRepository.findByUsername("claude69"))
                .isNotEmpty()
                .get()
                .extracting(UserEntity::getEmail)
                .isEqualTo("claude@gmail");
    }

    @Test
    void usernameNotFoundTest() {
        assertThat(userRepository.findByUsername("fake_name")).isEmpty();
    }

    @Test
    void findUserWithRolesTest() {
        var result = userRepository.findByEmailWithRoles("claude@gmail").orElseThrow();

        assertThat(result.getRoles()).isNotEmpty();

        assertThat(result)
                .extracting(UserEntity::getRoles)
                .extracting(Set::size)
                .isEqualTo(1);
    }
}
