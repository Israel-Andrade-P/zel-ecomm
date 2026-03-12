package com.zeldev.zel_e_comm.unittests.productservice;

import com.zeldev.zel_e_comm.exception.InsufficientStockException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ValidateQuantityTest extends ProductServiceBaseTest{

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    @DisplayName(
            """
                    GIVEN: a requested quantity and product id
                    WHEN: validateQuantity is called
                    THEN: validate quantity in stock
                    """
    )
    void greenPath(Integer requestedQuantity) {
        var product = createProduct("Playstation 5");
        var id = UUID.randomUUID();

        when(productRepository.findByPublicId(id)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productService.validateQuantity(requestedQuantity, id));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @DisplayName(
            """
                    GIVEN: a requested quantity and product id
                    WHEN: validateQuantity is called
                    THEN: quantity in stock not enough
                    AND: throws exception
                    """
    )
    void redPath(Integer inStock) {
        var product = createProduct("Playstation 5");
        product.setQuantity(inStock);
        var id = UUID.randomUUID();

        when(productRepository.findByPublicId(id)).thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class, () -> productService.validateQuantity(2, id));
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a requested quantity and product id
                    WHEN: validateQuantity is called
                    THEN: product doesn't exist
                    AND: exception is thrown
                    """
    )
    void shouldThrow() {
        UUID id = UUID.randomUUID();

        when(productRepository.findByPublicId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.validateQuantity(1, id));
    }
}
