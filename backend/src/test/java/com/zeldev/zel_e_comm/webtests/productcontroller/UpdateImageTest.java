package com.zeldev.zel_e_comm.webtests.productcontroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateImageTest extends ProductControllerBaseTest{
    private final String PATH = String.format("/manage/products/%s/image", PRODUCT_ID);

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName(
            """
                    GIVEN: a string path variable and a query param image
                    WHEN: it hits /manage/products/{product_id}/image
                    THEN: return 200 response with updated product in body
                    """
    )
    void greenPath() throws Exception {
        var dto = createDto("Pan", 2);
        MockMultipartFile fileImage = new MockMultipartFile("image", new byte[5]);

        when(productService.updateImage(eq(PRODUCT_ID), any()))
                .thenReturn(dto);

        mockMvc.perform(
                        multipart(BASE_URI.concat(PATH))
                                .file(fileImage)
                                .with(request -> {
                                    request.setMethod("PUT"); // override POST
                                    return request;
                                })
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));

        verify(productService).updateImage(eq(PRODUCT_ID), any());
    }
}
