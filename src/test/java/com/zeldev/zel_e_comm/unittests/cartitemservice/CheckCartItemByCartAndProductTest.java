package com.zeldev.zel_e_comm.unittests.cartitemservice;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.exception.CartItemAlreadyAddedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CheckCartItemByCartAndProductTest extends CartItemServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a cart entity, product entity
                    WHEN: checkCartItemByCartAndProduct is called
                    THEN: checks if item is already in user's cart
                    """
    )
    void greenPath() {
        ProductEntity product = new ProductEntity();
        product.setPublicId(UUID.randomUUID());
        product.setName("TV");
        product.setSpecialPrice(BigDecimal.valueOf(69.99));
        product.setDiscount(3);
        CartEntity cart = new CartEntity();
        cart.setId(5L);
        CartItemEntity item = new CartItemEntity();
        item.setProduct(product);

        when(cartItemRepository.findCartItemEntityByProductIdAndCartId(cart.getId(), product.getPublicId())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> cartItemService.checkCartItemByCartAndProduct(cart, product));
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a cart entity, product entity
                    WHEN: checkCartItemByCartAndProduct is called
                    THEN: checks if item is already in user's cart
                    AND: if it is throws exception
                    """
    )
    void redPath() {
        ProductEntity product = new ProductEntity();
        product.setPublicId(UUID.randomUUID());
        product.setName("TV");
        product.setSpecialPrice(BigDecimal.valueOf(69.99));
        product.setDiscount(3);
        CartEntity cart = new CartEntity();
        cart.setId(5L);
        CartItemEntity item = new CartItemEntity();
        item.setProduct(product);

        when(cartItemRepository.findCartItemEntityByProductIdAndCartId(cart.getId(), product.getPublicId())).thenReturn(Optional.of(item));

        assertThrows(CartItemAlreadyAddedException.class ,() -> cartItemService.checkCartItemByCartAndProduct(cart, product));
    }
}
