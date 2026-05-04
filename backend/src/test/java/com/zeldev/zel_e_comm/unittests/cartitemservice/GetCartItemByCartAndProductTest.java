package com.zeldev.zel_e_comm.unittests.cartitemservice;

import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetCartItemByCartAndProductTest extends CartItemServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a cart id, product id
                    WHEN: getCartItemByCartAndProduct is called
                    THEN: return corresponding cart item
                    """
    )
    void greenPath() {
        Long cartId = 2L;
        UUID productId = UUID.randomUUID();
        CartItemEntity item = new CartItemEntity();
        item.setQuantity(4);

        when(cartItemRepository.findCartItemEntityByProductIdAndCartId(cartId, productId)).thenReturn(Optional.of(item));

        var response = cartItemService.getCartItemByCartAndProduct(cartId, productId);

        assertEquals(item.getQuantity(), response.getQuantity());
        verify(cartItemRepository, times(1)).findCartItemEntityByProductIdAndCartId(cartId, productId);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a cart id, product id
                    WHEN: getCartItemByCartAndProduct is called
                    THEN: return empty optional
                    AND: throws exception
                    """
    )
    void redPath() {
        Long cartId = 2L;
        UUID productId = UUID.randomUUID();

        when(cartItemRepository.findCartItemEntityByProductIdAndCartId(cartId, productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartItemService.getCartItemByCartAndProduct(cartId, productId));
    }
}
