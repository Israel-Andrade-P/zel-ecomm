package com.zeldev.zel_e_comm.webtests.productcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

public class AddTest extends ProductControllerBaseTest {
    private final String CATEGORY_NAME = "Kitchen Stuff";
    private final String PATH = String.format("/seller/categories/%s/product", CATEGORY_NAME);

    @Test
    @DisplayName(
            """
                    GIVEN: a string path variable and a new product in body
                    WHEN: it hits /seller/categories/{category_name}/product
                    THEN: return 201 response with product in body
                    """
    )
    void greenPath() throws JsonProcessingException {
        var dto = createDto("Pan", 2);

        when(productService.create(dto, CATEGORY_NAME)).thenReturn(dto);

        mvc.post()
                .uri(BASE_URI.concat(PATH))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto))
                .exchange()
                .assertThat()
                .hasStatus(CREATED)
                .bodyJson()
                .isEqualTo(objectMapper.writeValueAsString(dto));

        verify(productService).create(dto, CATEGORY_NAME);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a string path variable and a new product in body with invalid fields
                    WHEN: it hits /seller/categories/{category_name}/product
                    THEN: return 400 response due to invalid arguments
                    """
    )
    void validationTest() throws JsonProcessingException {
        var dto = createDto(null, 0);
        mvc.post()
                .uri(BASE_URI.concat(PATH))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto))
                .exchange()
                .assertThat()
                .hasStatus(BAD_REQUEST);

        //verify(productService).create(dto, CATEGORY_NAME);
    }
}
