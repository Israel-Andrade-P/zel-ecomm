package com.zeldev.zel_e_comm.webtests.productcontroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.zeldev.zel_e_comm.constants.Constants.*;
import static java.lang.Integer.parseInt;
import static org.mockito.Mockito.verify;

public class GetAllBySellerTest extends ProductControllerBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: query params
                    WHEN: it hits /seller/products
                    THEN: return 200 response with products in body
                    """
    )
    void getBySellerTest() {
        mvc.get()
                .uri(BASE_URI.concat("/seller/products"))
                .exchange()
                .assertThat()
                .hasStatus(HttpStatus.OK);

        verify(productService).getProductsBySeller(
                parseInt(PAGE_NUMBER),
                parseInt(PAGE_SIZE),
                SORT_ENTITY_BY,
                SORT_DIR
        );
    }
}
