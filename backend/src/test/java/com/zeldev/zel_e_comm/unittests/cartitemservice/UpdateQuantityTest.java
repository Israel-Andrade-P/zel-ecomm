package com.zeldev.zel_e_comm.unittests.cartitemservice;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.exception.InsufficientStockException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateQuantityTest extends CartItemServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a cart entity, product id and quantity
                    WHEN: updateQuantity is called
                    THEN: updates item quantity in cart
                    """
    )
    void greenPath() {
        Integer quantity = 1;
        String productId = UUID.randomUUID().toString();
        CartEntity cart = new CartEntity();
        cart.setId(5L);
        CartItemEntity item = new CartItemEntity();
        item.setQuantity(2);
        cart.addItem(item);

        when(cartItemRepository.findCartItemEntityByProductIdAndCartId(cart.getId(), UUID.fromString(productId))).thenReturn(Optional.of(item));

        cartItemService.updateQuantity(cart, productId, quantity);

        assertEquals(3, item.getQuantity());

        verify(productService, times(1)).validateQuantity(3, UUID.fromString(productId));
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a cart entity, product id and quantity
                    WHEN: updateQuantity is called
                    THEN: updates item quantity in cart to 0
                    AND: removes item from cart
                    """
    )
    void greenPath2() {
        Integer quantity = -1;
        String productId = UUID.randomUUID().toString();
        CartEntity cart = new CartEntity();
        cart.setId(5L);
        CartItemEntity item = new CartItemEntity();
        item.setQuantity(1);
        cart.addItem(item);

        when(cartItemRepository.findCartItemEntityByProductIdAndCartId(cart.getId(), UUID.fromString(productId))).thenReturn(Optional.of(item));

        cartItemService.updateQuantity(cart, productId, quantity);

        assertNull(item.getCart());

        verify(productService, times(1)).validateQuantity(0, UUID.fromString(productId));
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a cart entity, product id and quantity
                    WHEN: updateQuantity is called
                    THEN: doesn't find the item in db
                    AND: throws exception
                    """
    )
    void redPath() {
        Integer quantity = -1;
        String productId = UUID.randomUUID().toString();
        CartEntity cart = new CartEntity();
        cart.setId(5L);

        when(cartItemRepository.findCartItemEntityByProductIdAndCartId(cart.getId(), UUID.fromString(productId))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartItemService.updateQuantity(cart, productId, quantity));

        verify(productService, never()).validateQuantity(anyInt(), any());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a cart entity, product id and quantity
                    WHEN: updateQuantity is called
                    THEN: productService throws exception due to insufficient stock
                    AND: propagates exception
                    """
    )
    void redPath2() {
        Integer quantity = 5;
        String productId = UUID.randomUUID().toString();
        CartEntity cart = new CartEntity();
        cart.setId(5L);

        CartItemEntity item = new CartItemEntity();
        item.setQuantity(10);

        when(cartItemRepository.findCartItemEntityByProductIdAndCartId(
                cart.getId(), UUID.fromString(productId)))
                .thenReturn(Optional.of(item));

        doThrow(new InsufficientStockException("Not enough stock"))
                .when(productService)
                .validateQuantity(15, UUID.fromString(productId));

        assertThrows(InsufficientStockException.class, () ->
                cartItemService.updateQuantity(cart, productId, quantity)
        );
    }
}
