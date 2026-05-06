package com.zeldev.zel_e_comm.datatests.cartitemrepo;

import com.zeldev.zel_e_comm.config.AppConfig;
import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.repository.CartItemRepository;
import com.zeldev.zel_e_comm.repository.CartRepository;
import com.zeldev.zel_e_comm.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(AppConfig.class)
public class CartItemRepositoryTest {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    private CartEntity cart;
    private ProductEntity product;

    @BeforeEach
    void setup() {
        cart = new CartEntity();
        product = new ProductEntity();
        product.setPublicId(UUID.randomUUID());

        var item = CartItemEntity.builder()
                .cart(cart)
                .product(product)
                .quantity(3)
                .price(new BigDecimal("25"))
                .discount(3)
                .build();

        cartRepository.save(cart);
        productRepository.save(product);
        cartItemRepository.save(item);
    }

    @Test
    void findCartItemEntityByProductIdAndCartIdTest() {
        var result = cartItemRepository.findCartItemEntityByProductIdAndCartId(cart.getId(), product.getPublicId());

        assertThat(result).isNotEmpty().get().extracting(CartItemEntity::getPrice).isEqualTo(new BigDecimal("25"));
    }
}
