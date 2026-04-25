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

public class GetCartsTest extends CartControllerBaseTest{

    @Test
    @DisplayName(
            """
                    WHEN: it hits admin/carts
                    THEN: returns a 200 with a List of CartDTOs in body
                    """
    )
    void greenPath() {
        CartDTO cartDTO1 = new CartDTO(
                new BigDecimal("4500"),
                List.of(new CartItemDTO("productId1", "TV", 1, new BigDecimal("4500"), 2))
        );
        CartDTO cartDTO2 = new CartDTO(
                new BigDecimal("45"),
                List.of(new CartItemDTO("productId2", "Towel", 3, new BigDecimal("15"), 2))
        );

        when(cartService.getCarts()).thenReturn(List.of(cartDTO1, cartDTO2));

        var result = mockMvc
                .get()
                .uri(BASE_URI.concat("/admin/carts"))
                .exchange();

        assertThat(result).hasStatus(OK);
        assertThat(result).bodyJson().extractingPath("$[0].products[0].productId").isEqualTo(cartDTO1.products().getFirst().productId());
        assertThat(result).bodyJson().extractingPath("$[1].products[0].productName").isEqualTo(cartDTO2.products().getFirst().productName());
    }

}
