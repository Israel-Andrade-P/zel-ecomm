package com.zeldev.zel_e_comm.unittests.cartservice;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UpdateQuantityTest extends CartServiceBaseTest {

    @Test
    @DisplayName(
            """
                    GIVEN: a product id and quantity
                    WHEN: updateQuantity is called
                    THEN: update quantity and return updated DTO
                    """
    )
    void greenPath() {
        String email = "zel@gmail";
        String productId = "someId";
        Integer quantity = 3;
        CartEntity cart = new CartEntity();

        when(authUtils.getLoggedInEmail()).thenReturn(email);
        when(cartRepository.findCartByUserEmail(email)).thenReturn(Optional.of(cart));

        var response = cartService.updateQuantity(productId, quantity);

        assertNotNull(response);
        verify(cartRepository, times(1)).findCartByUserEmail(email);
        verify(cartItemService, times(1)).updateQuantity(cart, productId, quantity);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a product id and quantity
                     WHEN: updateQuantity is called
                     THEN: return empty optional of cart entity
                     AND: throws exception
                    """
    )
    void redPath() {
        String email = "zel@gmail";

        when(authUtils.getLoggedInEmail()).thenReturn(email);
        when(cartRepository.findCartByUserEmail(email)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.updateQuantity("someId", 2));
    }
}
