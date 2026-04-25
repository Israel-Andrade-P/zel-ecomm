package com.zeldev.zel_e_comm.webtests.cartcontroller;

import com.zeldev.zel_e_comm.dto.request.CartDTO;
import com.zeldev.zel_e_comm.dto.request.CartItemDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class GetCartTest extends CartControllerBaseTest{

    @Test
    @DisplayName(
            """
                    WHEN: it hits /carts/users/cart
                    THEN: returns a 200 with a CartDTO in body
                    """
    )
    void greenPath() {
        CartDTO cart = new CartDTO(
                new BigDecimal("4500"),
                List.of(new CartItemDTO("productId1", "TV", 1, new BigDecimal("4500"), 2))
        );

        when(cartService.getCart()).thenReturn(cart);

        var result = mockMvc
                .get()
                .uri(BASE_URI.concat("/carts/users/cart"))
                .exchange();

        assertThat(result).hasStatus(OK);
        assertThat(result).bodyJson().extractingPath("$.products[0].productId").isEqualTo(cart.products().getFirst().productId());
        assertThat(result).bodyJson().extractingPath("$.products[0].productName").isEqualTo(cart.products().getFirst().productName());
    }
}
