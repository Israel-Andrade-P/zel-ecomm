package com.zeldev.zel_e_comm.datatests.productrepo;

import com.zeldev.zel_e_comm.config.AppConfig;
import com.zeldev.zel_e_comm.entity.CategoryEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.repository.CategoryRepository;
import com.zeldev.zel_e_comm.repository.ProductRepository;
import com.zeldev.zel_e_comm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static com.zeldev.zel_e_comm.enumeration.UserStatus.ACTIVE;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(AppConfig.class)
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    private final UUID PROD_ID = UUID.randomUUID();
    private CategoryEntity category;
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
        userRepository.save(user);

        category = categoryRepository.save(CategoryEntity.builder().name("Kitchen Stuff").build());

        var p1 = ProductEntity.builder().name("Stove").price(new BigDecimal("100")).quantity(2).discount(3).description("Cool prod bruh")
                .publicId(PROD_ID).specialPrice(new BigDecimal("80")).image("img.png").category(category).seller(user).build();
        var p2 = ProductEntity.builder().name("Fridge").price(new BigDecimal("80")).quantity(1).discount(3).description("Cool prod bruh")
                .publicId(UUID.randomUUID()).specialPrice(new BigDecimal("80")).image("img.png").category(category).seller(user).build();


        productRepository.save(p1);
        productRepository.save(p2);
    }

    @Test
    void findByPublicIdTest() {
        var result = productRepository.findByPublicId(PROD_ID);
        assertThat(result)
                .isNotEmpty()
                .get()
                .extracting(ProductEntity::getName)
                .isEqualTo("Stove");
        assertThat(result)
                .get()
                .extracting(ProductEntity::getCreatedAt)
                .isNotNull();
    }

    @Test
    void isEmptyTest() {
        var result = productRepository.findByPublicId(UUID.randomUUID());
        assertThat(result)
                .isEmpty();
    }

    @Test
    void findByCategoryIdOrderByPriceAscTest() {
        var result = productRepository.findByCategory_IdOrderByPriceAsc(category.getId(), PageRequest.of(0, 10));

        assertThat(result.getTotalElements()).isEqualTo(2L);
        assertThat(result.getContent())
                .hasSize(2)
                .extracting(ProductEntity::getName)
                .containsExactly("Fridge", "Stove");
    }

    @Test
    void isEmpty2Test() {
        var result = productRepository.findByCategory_IdOrderByPriceAsc(500L, PageRequest.of(0, 10));
        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"fri", "VE"})
    void findByNameLikeIgnoreCaseTest(String someString) {
        var result = productRepository.findByNameLikeIgnoreCase(someString, PageRequest.of(0, 10));

        assertThat(result.getTotalElements()).isEqualTo(1L);
        assertThat(result.getContent())
                .hasSize(1)
                .extracting(ProductEntity::getName)
                .containsExactly(result.getContent().getFirst().getName());
    }

    @Test
    void isEmpty3Test() {
        var result = productRepository.findByNameLikeIgnoreCase("xyz", PageRequest.of(0, 10));
        assertThat(result).isEmpty();
    }

    @Test
    void findBySellerEmailTest() {
        var result = productRepository.findBySellerEmail(user.getEmail(), PageRequest.of(0, 10));

        assertThat(result.getTotalElements()).isEqualTo(2L);
        assertThat(result.getContent())
                .hasSize(2)
                .extracting(ProductEntity::getName)
                .containsExactly("Stove", "Fridge");
    }

    @Test
    void isEmpty4Test() {
        var result = productRepository.findBySellerEmail("other@mail", PageRequest.of(0, 10));
        assertThat(result).isEmpty();
    }

    @Test
    void existsByIdAndSellerEmailTest() {
        var result = productRepository.existsByIdAndSellerEmail(PROD_ID, "fake@mail");

        assertThat(result).isTrue();
    }

    @Test
    void existsByIdAndSellerEmailTest2() {
        var result = productRepository.existsByIdAndSellerEmail(PROD_ID, "other@mail");

        assertThat(result).isFalse();
    }
}
