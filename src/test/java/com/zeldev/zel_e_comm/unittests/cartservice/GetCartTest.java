package com.zeldev.zel_e_comm.unittests.cartservice;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetCartTest extends CartServiceBaseTest{

    @Test
    @DisplayName(
            """
                    WHEN: getCart is called
                    THEN: returns a CartDTO associated with the logged in user
                    """
    )
    void greenPath() {
        String email = "zel@gmail";
        CartEntity cart = new CartEntity();

        when(authUtils.getLoggedInEmail()).thenReturn(email);
        when(cartRepository.findCartByUserEmail(email)).thenReturn(Optional.of(cart));

        var response = cartService.getCart();

        assertNotNull(response);
        verify(authUtils, times(1)).getLoggedInEmail();
        verify(cartRepository, times(1)).findCartByUserEmail(email);
    }

    @Test
    @DisplayName(
            """
                    WHEN: getCart is called
                    THEN: returns an empty Optional
                    AND: throws exception
                    """
    )
    void redPath() {
        String email = "zel@gmail";

        when(authUtils.getLoggedInEmail()).thenReturn(email);
        when(cartRepository.findCartByUserEmail(email)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.getCart());
    }
}
