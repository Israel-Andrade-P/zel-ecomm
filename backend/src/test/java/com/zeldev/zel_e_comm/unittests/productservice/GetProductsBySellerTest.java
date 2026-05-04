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

public class GetProductsBySellerTest extends ProductServiceBaseTest{
    private final String EMAIL = "test@gmail";

    @ParameterizedTest
    @ValueSource(strings = {"asc", "desc"})
    @DisplayName(
            """
                    GIVEN: some pagination related request params
                    WHEN: getProductsBySeller is called
                    THEN: return a ProductResponse with logged in seller products
                    """
    )
    void shouldReturnProductsBySeller(String sortOrder) {
        int page = 0;
        int size = 10;


        Page<ProductEntity> productPage = new PageImpl<>(List.of(createProduct("Refrigerator"), createProduct("Chair")));

        when(authUtils.getLoggedInEmail()).thenReturn(EMAIL);
        when(productRepository.findBySellerEmail(any(String.class), any(Pageable.class))).thenReturn(productPage);

        ProductResponse response = productService.getProductsBySeller(page, size, "name", sortOrder);

        Pageable pageable = capturePageable();

        assertPageable(pageable, page, size, "name", sortOrder.equals("asc") ? ASC : DESC);

        assertEquals(2, response.content().size());
        assertEquals(page, response.pageNumber());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: some pagination related request params
                    WHEN: getProductsBySeller is called
                    THEN: no products in db yet
                    AND: throws exception
                    """
    )
    void throwsException() {
        int page = 0;
        int size = 10;

        Page<ProductEntity> page1 = new PageImpl<>(List.of());

        when(authUtils.getLoggedInEmail()).thenReturn(EMAIL);
        when(productRepository.findBySellerEmail(any(String.class), any(Pageable.class))).thenReturn(page1);

        assertThrows(APIException.class, () -> productService.getProductsBySeller(page, size, "name", "asc"));
    }

    private Pageable capturePageable() {
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(productRepository, times(1)).findBySellerEmail(any(String.class), captor.capture());
        return captor.getValue();
    }
}
