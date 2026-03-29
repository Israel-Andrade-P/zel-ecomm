package com.zeldev.zel_e_comm.webtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeldev.zel_e_comm.dto.request.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.CREATED;

public class AddTest extends ProductControllerBaseTest {
    private final String CATEGORY_NAME = "Kitchen Stuff";
    private final String PATH = String.format("/seller/categories/%s/product", CATEGORY_NAME);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName(
            """
                    GIVEN: a string path variable and a new product in body
                    WHEN: it hits /seller/categories/{category_name}/product
                    THEN: return 201 response with product in body
                    """
    )
    @WithMockUser(username = "zel", roles = {"SELLER"})
    void greenPath() throws JsonProcessingException {
        var dto = createDto();
        mvc.post()
                .uri(BASE_URI.concat(PATH))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto))
                .exchange()
                .assertThat()
                .hasStatus(CREATED);

        //verify(productService).getAllProducts(3, 35, "price", "desc");
    }

    private ProductDTO createDto() {
        return new ProductDTO(
                "1",
                "Pan",
                "Nice pan",
                "pan.png",
                5,
                new BigDecimal("100.00"),
                0,
                new BigDecimal("90.00")
        );
    }
}
