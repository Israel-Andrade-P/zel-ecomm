package com.zeldev.zel_e_comm.unittests.cartitemservice;

import com.zeldev.zel_e_comm.entity.CartItemEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LeftOverTests extends CartItemServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: product id
                    WHEN: findActiveCartItemsByProductId is called
                    THEN: return a list of cart items
                    """
    )
    void findActiveCartItemsByProductIdTest() {
        UUID productId = UUID.randomUUID();
        CartItemEntity item1 = new CartItemEntity();
        item1.setQuantity(4);
        CartItemEntity item2 = new CartItemEntity();
        item2.setQuantity(2);
        List<CartItemEntity> items = List.of(item1,item2);

        when(cartItemRepository.findActiveCartItemsByProductId(productId)).thenReturn(items);

        var response = cartItemService.findActiveCartItemsByProductId(productId);

        assertEquals(items.size(), response.size());
        assertEquals(items, response);
        verify(cartItemRepository).findActiveCartItemsByProductId(productId);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: product id
                    WHEN: deleteByProductPublicId is called
                    THEN: return a list of cart items
                    """
    )
    void deleteByProductPublicIdTest() {
        UUID productId = UUID.randomUUID();

        cartItemService.deleteByProductPublicId(productId);

        verify(cartItemRepository).deleteByProductPublicId(productId);
    }
}
