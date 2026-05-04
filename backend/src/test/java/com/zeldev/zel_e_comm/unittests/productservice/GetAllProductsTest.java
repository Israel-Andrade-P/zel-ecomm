package com.zeldev.zel_e_comm.unittests.productservice;

import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.exception.APIException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

public class GetAllProductsTest extends ProductServiceBaseTest {

    @ParameterizedTest
    @ValueSource(strings = {"asc", "desc"})
    @DisplayName(
            """
                    GIVEN: some pagination related request params 
                    WHEN: getAllProducts is called
                    THEN: return a ProductResponse
                    """
    )
    void shouldReturnProducts(String sortOrder) {
        int page = 0;
        int size = 10;
        String sortBy = "name";

        Page<ProductEntity> productPage = new PageImpl<>(List.of(createProduct("Refrigerator"), createProduct("Chair")));

        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);

        ProductResponse response = productService.getAllProducts(page, size, sortBy, sortOrder);

        Pageable pageable = capturePageable();

        assertPageable(pageable, page, size, sortBy, sortOrder.equals("asc") ? ASC : DESC);

        assertEquals(2, response.content().size());
        assertEquals(page, response.pageNumber());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: some pagination related request params 
                    WHEN: getAllProducts is called
                    THEN: no products in db yet
                    AND: throws exception
                    """
    )
    void throwsException() {
        int page = 0;
        int size = 10;

        Page<ProductEntity> page1 = new PageImpl<>(List.of());

        when(productRepository.findAll(any(Pageable.class))).thenReturn(page1);

        assertThrows(APIException.class, () -> productService.getAllProducts(page, size, "name", "asc"));
    }

    private Pageable capturePageable() {
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(productRepository, times(1)).findAll(captor.capture());
        return captor.getValue();
    }
}
