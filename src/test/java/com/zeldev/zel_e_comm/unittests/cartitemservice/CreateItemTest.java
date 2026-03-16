package com.zeldev.zel_e_comm.unittests.cartitemservice;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateItemTest extends CartItemServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a cart entity, product entity and quantity
                    WHEN: createItem is called
                    THEN: create and persist a new item
                    """
    )
    void greenPath() {
        Integer quantity = 2;
        ProductEntity product = new ProductEntity();
        product.setSpecialPrice(BigDecimal.valueOf(69.99));
        product.setDiscount(3);
        CartEntity cart = new CartEntity();

//        when(cartItemRepository.save(any())).thenAnswer(InvocationOnMock::getArguments);

        CartItemEntity output = cartItemService.createItem(product, cart, quantity);

        ArgumentCaptor<CartItemEntity> captor = ArgumentCaptor.forClass(CartItemEntity.class);

        verify(cartItemRepository, times(1)).save(captor.capture());

        CartItemEntity item = captor.getValue();

        assertEquals(product.getSpecialPrice(), item.getPrice());
        assertEquals(product.getDiscount(), item.getDiscount());
        assertEquals(cart, item.getCart());
        assertEquals(quantity, item.getQuantity());
    }
}
