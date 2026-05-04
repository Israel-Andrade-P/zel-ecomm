package com.zeldev.zel_e_comm.unittests.productservice;

import com.zeldev.zel_e_comm.exception.InsufficientStockException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DecreaseStockTest extends ProductServiceBaseTest{

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    @DisplayName(
            """
                    GIVEN: a product id and a requested quantity
                    WHEN: decreaseStock is called
                    THEN: deducts from stock
                    """
    )
    void greenPath(Integer requestedQuantity) {
        var product = createProduct("Playstation 5");
        var stock = product.getQuantity();
        var id = UUID.randomUUID();

        when(productRepository.findByPublicId(id)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productService.decreaseStock(id, requestedQuantity));
        assertEquals(stock - requestedQuantity, product.getQuantity());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a product id and a requested quantity
                    WHEN: decreaseStock is called
                    THEN: not enough in stock
                    AND: throws exception
                    """
    )
    void redPath() {
        var product = createProduct("Playstation 5");
        var stock = product.getQuantity();
        var id = UUID.randomUUID();

        when(productRepository.findByPublicId(id)).thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class, () -> productService.decreaseStock(id, 3));
        assertEquals(stock, product.getQuantity());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a invalid product id and a requested quantity
                    WHEN: decreaseStock is called
                    THEN: product doesn't exist
                    AND: throws exception
                    """
    )
    void shouldThrow() {
        UUID id = UUID.randomUUID();

        when(productRepository.findByPublicId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.decreaseStock(id, 2));
    }
}
