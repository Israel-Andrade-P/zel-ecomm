package com.zeldev.zel_e_comm.datatests.cartrepo;

import com.zeldev.zel_e_comm.config.AppConfig;
import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.repository.CartRepository;
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
public class CartRepositoryTest {
    @Autowired
    private CartRepository cartRepository;
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

        var cart = new CartEntity();
        cart.setUser(user);

        userRepository.save(user);
        cartRepository.save(cart);
    }

    @Test
    void findCartByUserEmailTest() {
        var result = cartRepository.findCartByUserEmail(user.getEmail());

        assertThat(result).isNotEmpty().get().extracting(CartEntity::getUser).isEqualTo(user);
    }

    @Test
    void redPath() {
        var result = cartRepository.findCartByUserEmail("fake@gmail");

        assertThat(result).isEmpty();
    }
}
