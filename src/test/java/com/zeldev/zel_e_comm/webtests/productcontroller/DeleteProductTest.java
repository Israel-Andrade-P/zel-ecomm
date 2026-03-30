package com.zeldev.zel_e_comm.webtests.productcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class DeleteProductTest extends ProductControllerBaseTest{
    private final String PATH = String.format("/manage/products/delete/%s", PRODUCT_ID);

    @Test
    @DisplayName(
            """
                    GIVEN: a path variable product id
                    WHEN: it hits /manage/products/delete/{product_id}
                    THEN: return 200 response with deleted product in body
                    """
    )
    void greenPath() throws JsonProcessingException {
        var dto = createDto("Pan", 2);
        when(orchestrationService.deleteCartItemsAfterProduct(PRODUCT_ID)).thenReturn(dto);

        mvc.delete()
                .uri(BASE_URI.concat(PATH))
                .exchange()
                .assertThat()
                .hasStatus(OK)
                .bodyJson()
                .isEqualTo(objectMapper.writeValueAsString(dto));

        verify(orchestrationService).deleteCartItemsAfterProduct(PRODUCT_ID);
    }
}
