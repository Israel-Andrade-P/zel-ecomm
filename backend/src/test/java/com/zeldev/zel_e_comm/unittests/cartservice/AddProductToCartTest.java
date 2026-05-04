package com.zeldev.zel_e_comm.unittests.cartservice;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AddProductToCartTest extends CartServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a product id and a requested quantity
                    WHEN: addProductToCart is called
                    THEN: adds a product to the user's cart
                    """
    )
    void greenPath() {
        String publicId = "publicId";
        CartEntity cart = new CartEntity();
        ProductEntity product = new ProductEntity();
        product.setPublicId(UUID.randomUUID());
        product.setName("Coffee Machine");
        product.setPrice(BigDecimal.valueOf(39.99));

        UserEntity user = new UserEntity();
        user.setEmail("zel@gmail");

        when(authUtils.getLoggedInUser()).thenReturn(user);
        when(cartRepository.findCartByUserEmail(user.getEmail())).thenReturn(Optional.of(cart));
        when(productService.findByPublicId(publicId)).thenReturn(product);

        var response = cartService.addProductToCart(publicId, 2);

       verify(cartItemService, times(1)).checkCartItemByCartAndProduct(cart, product);
       verify(productService, times(1)).validateQuantity(2, product.getPublicId());
       verify(cartRepository, times(1)).save(cart);
       verify(cartItemService, times(1)).createItem(product, cart, 2);

       assertNotNull(response);
    }
}
