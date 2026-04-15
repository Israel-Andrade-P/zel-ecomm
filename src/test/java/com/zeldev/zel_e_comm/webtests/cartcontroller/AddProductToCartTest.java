package com.zeldev.zel_e_comm.webtests.cartcontroller;

import com.zeldev.zel_e_comm.dto.request.CartDTO;
import com.zeldev.zel_e_comm.dto.request.CartItemDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AddProductToCartTest extends CartControllerBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a product id and quantity
                    WHEN: it hits /carts/products/{product_id}/quantity/{quantity}
                    THEN: returns a 201 with a CartDTO in body
                    """
    )
    void greenPath() {
        String productId = "someId";
        Integer quantity = 3;
        CartDTO cartDTO = new CartDTO(
                new BigDecimal("499"),
                List.of(new CartItemDTO(productId, "TV", quantity, new BigDecimal("45"), 2))
                );

        when(cartService.addProductToCart(productId, quantity)).thenReturn(cartDTO);

        var result = mockMvc
                .post()
                .uri(BASE_URI.concat(String.format("/carts/products/%s/quantity/%d", productId, quantity)))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson().extractingPath("$.totalPrice").isEqualTo(499);
        assertThat(result).bodyJson().extractingPath("$.products[0].productId").isEqualTo(productId);
    }
}
