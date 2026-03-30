package com.zeldev.zel_e_comm.webtests.productcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class UpdateProductTest extends ProductControllerBaseTest{
    private final String PATH = String.format("/manage/products/update/%s", PRODUCT_ID);

    @Test
    @DisplayName(
            """
                    GIVEN: a string path variable and a new product in body
                    WHEN: it hits /manage/products/update/{product_id}
                    THEN: return 200 response with updated product in body
                    """
    )
    void greenPath() throws JsonProcessingException {
        var dto = createDto("Pan", 2);

        when(orchestrationService.updateProductAndSyncCarts(dto, PRODUCT_ID)).thenReturn(dto);

        mvc.put()
                .uri(BASE_URI.concat(PATH))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto))
                .exchange()
                .assertThat()
                .hasStatus(OK)
                .bodyJson()
                .isEqualTo(objectMapper.writeValueAsString(dto));

        verify(orchestrationService).updateProductAndSyncCarts(dto, PRODUCT_ID);
    }
}
