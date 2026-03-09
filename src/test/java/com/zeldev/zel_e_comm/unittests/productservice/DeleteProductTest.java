package com.zeldev.zel_e_comm.unittests.productservice;

import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeleteProductTest extends ProductServiceBaseTest {

    @Test
    @DisplayName(
            """
                    GIVEN: a string id of a product
                    WHEN: deleteProduct is called
                    THEN: call delete repo method
                    """
    )
    void greenPath() {
        String id = UUID.randomUUID().toString();
        var product = createProduct("TV");

        when(productRepository.findByPublicId(UUID.fromString(id))).thenReturn(Optional.of(product));

        var response = productService.deleteProduct(id);

        assertEquals(product.getName(), response.name());
        assertEquals(product.getDescription(), response.description());
        assertEquals(product.getPrice(), response.price());
        assertEquals(product.getDiscount(), response.discount());

        verify(productRepository, times(1)).findByPublicId(UUID.fromString(id));
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: an invalid string id of a product
                    WHEN: deleteProduct is called
                    THEN: call delete repo method
                    AND: throws exception
                    """
    )
    void redPath() {
        String publicId = UUID.randomUUID().toString();

        when(productRepository.findByPublicId(UUID.fromString(publicId))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                productService.deleteProduct(publicId));

        verify(productRepository, never()).delete(any());
    }
}
