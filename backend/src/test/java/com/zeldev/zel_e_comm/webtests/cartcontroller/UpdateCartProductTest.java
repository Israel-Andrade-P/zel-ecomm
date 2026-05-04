package com.zeldev.zel_e_comm.webtests.cartcontroller;

import com.zeldev.zel_e_comm.dto.request.CartDTO;
import com.zeldev.zel_e_comm.dto.request.CartItemDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class UpdateCartProductTest extends CartControllerBaseTest {

    @Test
    @DisplayName(
            """
                    WHEN: it hits carts/products/{product_id}/quantity/increase
                    THEN: returns a 200 with a updated CartDTO in body
                    """
    )
    void incrementPath() {
        var cart = createCartDTO();
        var productId = cart.products().getFirst().productId();

        when(cartService.updateQuantity(productId, 1)).thenReturn(cart);

        var result = mockMvc
                .put()
                .uri(BASE_URI.concat(String.format("/carts/products/%s/quantity/increase", productId)))
                .exchange();

        assertThat(result).hasStatus(OK);
        assertThat(result).bodyJson().extractingPath("$.products[0].productId").isEqualTo(productId);
        assertThat(result).bodyJson().extractingPath("$.products[0].productName").isEqualTo("TV");
        verify(cartService).updateQuantity(productId, 1);
    }

    @Test
    @DisplayName(
            """
                    WHEN: it hits carts/products/{product_id}/quantity/decrease
                    THEN: returns a 200 with a updated CartDTO in body
                    """
    )
    void decrementPath() {
        var cart = createCartDTO();
        var productId = cart.products().getFirst().productId();

                when(cartService.updateQuantity(productId, -1)).thenReturn(cart);

        var result = mockMvc
                .put()
                .uri(BASE_URI.concat(String.format("/carts/products/%s/quantity/decrease", productId)))
                .exchange();

        assertThat(result).hasStatus(OK);
        verify(cartService).updateQuantity(productId, -1);
    }

    private CartDTO createCartDTO() {
        String productId = "productId";
        return new CartDTO(
                new BigDecimal("4500"),
                List.of(new CartItemDTO(productId, "TV", 1, new BigDecimal("4500"), 2))
        );
    }
}