package com.zeldev.zel_e_comm.webtests.cartcontroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;

public class DeleteItemTest extends CartControllerBaseTest{

    @Test
    @DisplayName(
            """
                    WHEN: it hits /carts/products/{product_id}
                    THEN: returns a 200 with a Response in body
                    """
    )
    void incrementPath() {
        String productId = "productId";

        var result = mockMvc
                .delete()
                .uri(BASE_URI.concat(String.format("/carts/products/%s", productId)))
                .exchange();

        assertThat(result).hasStatus(OK);
        verify(cartService).deleteItemFromCart(productId);
    }
}
