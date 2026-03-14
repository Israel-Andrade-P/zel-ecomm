package com.zeldev.zel_e_comm.unittests.cartservice;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeleteItemFromCartTest extends CartServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a product id
                    WHEN: deleteItemFromCart is called
                    THEN: remove corresponding item from user's cart
                    """
    )
    void greenPath() {
        String email = "zel@gmail";
        UUID productId = UUID.randomUUID();
        String stringId = productId.toString();
        CartEntity cart = new CartEntity();
        cart.setId(1L);
        CartItemEntity item = new CartItemEntity();
        cart.addItem(item);

        when(authUtils.getLoggedInEmail()).thenReturn(email);
        when(cartRepository.findCartByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartItemService.getCartItemByCartAndProduct(cart.getId(), productId)).thenReturn(item);

        cartService.deleteItemFromCart(stringId);

        assertNull(item.getCart());
        verify(cartItemService, times(1)).getCartItemByCartAndProduct(cart.getId(), productId);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a product id
                    WHEN: deleteItemFromCart is called
                    THEN: no cart associated with logged in user
                    AND: throws exception
                    """
    )
    void redPath() {
        String email = "zel@gmail";

        when(authUtils.getLoggedInEmail()).thenReturn(email);
        when(cartRepository.findCartByUserEmail(email)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.deleteItemFromCart("someId"));
    }
}
