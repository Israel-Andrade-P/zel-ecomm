package com.zeldev.zel_e_comm.unittests.productservice;

import com.zeldev.zel_e_comm.dto.request.ProductDTO;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UpdateProductTest extends ProductServiceBaseTest{

    @ParameterizedTest
    @MethodSource("fieldCases")
    @DisplayName(
            """
                    GIVEN: a productDTO and a string id
                    WHEN: updateProduct is called
                    THEN: update non null non blank fields of product entity
                    AND: return updated product response
                    """
    )
    void shouldUpdate(String name, String description, Integer quantity) {
        String publicId = UUID.randomUUID().toString();
        var productDto = new ProductDTO(
                null,
                name,
                description,
                null,
                quantity,
                null,
                null,
                null);
        var productDB = createProduct("iPhone15");
        productDB.setSpecialPrice(productDB.calculateSpecialPrice());

        when(productRepository.findByPublicId(UUID.fromString(publicId))).thenReturn(Optional.of(productDB));

        var response = productService.updateProduct(productDto, publicId);

        if (name != null && !name.isBlank()) assertEquals(productDto.name(), response.name());
        else assertEquals(productDB.getName(), response.name());

        if (description != null && !description.isBlank()) assertEquals(productDto.description(), response.description());
        else assertEquals(productDB.getDescription(), response.description());

        if (quantity != null) assertEquals(productDto.quantity(), response.quantity());
        else assertEquals(productDB.getQuantity(), response.quantity());

        assertEquals(productDB.getPrice(), response.price());
        assertEquals(productDB.getDiscount(), response.discount());
        assertEquals(BigDecimal.valueOf(1960.0).setScale(2, RoundingMode.HALF_UP), response.specialPrice());

        verify(productRepository).findByPublicId(UUID.fromString(publicId));
    }

    @ParameterizedTest
    @MethodSource("specialPriceCases")
    @DisplayName(
            """
                    GIVEN: a productDTO and a string id
                    WHEN: updateProduct is called
                    THEN: update only non null fields
                    AND: return updated product response
                    """
    )
    void shouldHandleSpecialPriceRecalc(BigDecimal price, Integer discount, boolean shouldRecalc) {
        String publicId = UUID.randomUUID().toString();

        var productDto = new ProductDTO(
                null,
                null,
                null,
                null,
                null,
                price,
                discount,
                null);

        var productDB = createProduct("iPhone15");
        productDB.setSpecialPrice(productDB.calculateSpecialPrice());

        when(productRepository.findByPublicId(UUID.fromString(publicId))).thenReturn(Optional.of(productDB));

        var response = productService.updateProduct(productDto, publicId);

        if (shouldRecalc) assertEquals(productDB.calculateSpecialPrice(), response.specialPrice());
        else assertEquals(productDB.getSpecialPrice(), response.specialPrice());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a productDTO and a invalid string id
                    WHEN: updateProduct is called
                    THEN: no product in db with provided id
                    AND: throws exception
                    """
    )
    void shouldThrow() {
        String publicId = UUID.randomUUID().toString();

        when(productRepository.findByPublicId(UUID.fromString(publicId))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                productService.updateProduct(
                        new ProductDTO(null,null,null,null,null,null,null,null),
                        publicId));
    }
}
