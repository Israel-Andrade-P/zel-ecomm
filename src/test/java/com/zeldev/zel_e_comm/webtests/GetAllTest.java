package com.zeldev.zel_e_comm.webtests;

import com.zeldev.zel_e_comm.dto.request.ProductDTO;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.List;

import static com.zeldev.zel_e_comm.constants.Constants.*;
import static java.lang.Integer.parseInt;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class GetAllTest extends ProductControllerBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: custom query params
                    WHEN: it hits /products
                    THEN: return 200 response with products in body
                    """
    )
    @WithMockUser
    void withCustomParams() {
        mvc.get()
                .uri(BASE_URI.concat("/products"))
                .param("page", "3")
                .param("size", "35")
                .param("sortBy", "price")
                .param("sortOrder", "desc")
                .exchange().assertThat().hasStatus(OK);

        verify(productService).getAllProducts(3, 35, "price", "desc");
    }

    @Test
    @DisplayName(
            """
                    GIVEN: default query params
                    WHEN: it hits /products
                    THEN: return 200 response with products in body
                    """
    )
    @WithMockUser
    void withDefaultParams() {
        var productResponse = getResponseDto();
        when(productService.getAllProducts(anyInt(), anyInt(), anyString(), anyString())).thenReturn(productResponse);
        mvc.get()
                .uri(BASE_URI.concat("/products"))
                .exchange().assertThat()
                .hasStatus(OK)
                .bodyJson()
                .extractingPath("$.content[0].name")
                .isEqualTo("tv");

        verify(productService).getAllProducts(parseInt(PAGE_NUMBER), parseInt(PAGE_SIZE), SORT_ENTITY_BY, SORT_DIR);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: default query params with no auth object
                    WHEN: it hits /products
                    THEN: return 401 response
                    """
    )
    void noAuthObj() {
        mvc.get()
                .uri(BASE_URI.concat("/products"))
                .exchange().assertThat()
                .hasStatus(UNAUTHORIZED);

        verify(productService, never()).getAllProducts(parseInt(PAGE_NUMBER), parseInt(PAGE_SIZE), SORT_ENTITY_BY, SORT_DIR);
    }

    private ProductResponse getResponseDto() {
        var products = List.of(new ProductDTO("id", "tv", "nice tv", "image.png", 1, new BigDecimal("5000"), 3, new BigDecimal("4800")));
        return ProductResponse.builder()
                .content(products)
                .build();
    }
}