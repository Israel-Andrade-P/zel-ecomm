package com.zeldev.zel_e_comm.unittests.productservice;

import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UpdateImageTest extends ProductServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a product id and a image file
                    WHEN: updateImage is called
                    THEN: updates product image
                    """
    )
    void greenPath() throws IOException {
        var product = createProduct("Playstation 5");
        var id = UUID.randomUUID().toString();
        MultipartFile fileImage = new MockMultipartFile("dogImage", new byte[5]);
        String updatedImage = "updated_image";
        String path = "/images/";

        when(productRepository.findByPublicId(UUID.fromString(id))).thenReturn(Optional.of(product));

        when(appConfig.getImages()).thenReturn(path);

        when(fileService.uploadImage(path, fileImage)).thenReturn(updatedImage);

        var response = productService.updateImage(id, fileImage);

        assertEquals(updatedImage, response.image());

        verify(productRepository, times(1)).findByPublicId(UUID.fromString(id));
        verify(fileService, times(1)).uploadImage(path, fileImage);
        verify(appConfig, times(1)).getImages();
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a product id and a image file
                    WHEN: updateImage is called
                    THEN: updates product image
                    """
    )
    void shouldThrow() {
        String id = UUID.randomUUID().toString();
        MultipartFile fileImage = new MockMultipartFile("dogImage", new byte[5]);

        when(productRepository.findByPublicId(UUID.fromString(id))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateImage(id, fileImage));
    }
}
