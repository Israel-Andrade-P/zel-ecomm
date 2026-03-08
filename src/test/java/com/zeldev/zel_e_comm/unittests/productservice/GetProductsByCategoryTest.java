package com.zeldev.zel_e_comm.unittests.productservice;

import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.entity.CategoryEntity;
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

public class GetProductsByCategoryTest extends ProductServiceBaseTest{

    @ParameterizedTest
    @ValueSource(strings = {"asc", "desc"})
    @DisplayName(
            """
            GIVEN: some pagination related request params and category id
            WHEN: getProductsByCategory is called
            THEN: return a ProductResponse
                    """
    )
    void shouldReturnProductsByCategory(String sortOrder) {
        int page = 0;
        int size = 10;

        Page<ProductEntity> productPage = new PageImpl<>(List.of(createProduct("Refrigerator"), createProduct("Chair")));

        CategoryEntity category = new CategoryEntity();
        category.setId(3L);
        category.setName("Kitchen stuff");

        String category_id = "3";
        when(categoryService.getByName(category_id)).thenReturn(category);
        when(productRepository.findByCategory_IdOrderByPriceAsc(any(Long.class), any(Pageable.class))).thenReturn(productPage);

        ProductResponse response = productService.getProductsByCategory(category_id, page, size, "name", sortOrder);

        Pageable pageable = capturePageable();

        assertPageable(pageable, page, size, "name", sortOrder.equals("asc") ? ASC : DESC);

        assertEquals(2, response.content().size());
        assertEquals(page, response.pageNumber());
    }

    @Test
    @DisplayName(
            """
            GIVEN: some pagination related request params and category id
            WHEN: getProductsByCategory is called
            THEN: return an empty list
            AND: throws exception
                    """
    )
    void throwsException() {
        int page = 0;
        int size = 10;
        String id = "3";

        Page<ProductEntity> productPage = new PageImpl<>(List.of());

        CategoryEntity category = new CategoryEntity();
        category.setId(3L);
        category.setName("Kitchen stuff");

        when(categoryService.getByName(id)).thenReturn(category);
        when(productRepository.findByCategory_IdOrderByPriceAsc(any(Long.class), any(Pageable.class))).thenReturn(productPage);

        assertThrows(APIException.class, () -> productService.getProductsByCategory(id, page, size, "name", "asc"));
    }

    protected Pageable capturePageable() {
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(productRepository, times(1)).findByCategory_IdOrderByPriceAsc(any(Long.class), captor.capture());
        return captor.getValue();
    }
}
