package com.zeldev.zel_e_comm.unittests.productservice;

import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

public class GetAllProductsTest extends ProductServiceBaseTest{

    @Test
    @DisplayName(
            """
            GIVEN: some pagination related request params 
            WHEN: getAllProducts is called
            THEN: return a ProductResponse
                    """
    )
    void greenPath() {
        int page = 0;
        int size = 10;

        ProductEntity product1 = new ProductEntity();
        product1.setPublicId(UUID.randomUUID());
        product1.setName("Refrigerator");

        ProductEntity product2 = new ProductEntity();
        product2.setPublicId(UUID.randomUUID());
        product2.setName("Chair");

        Page<ProductEntity> page1 = new PageImpl<>(List.of(product1, product2));

        when(productRepository.findAll(any(Pageable.class))).thenReturn(page1);

        ProductResponse response = productService.getAllProducts(page, size, "name", "asc");

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(productRepository, times(1)).findAll(captor.capture());

        Pageable pageable = captor.getValue();

        assertEquals("name", pageable.getSort().iterator().next().getProperty());
        assertEquals(ASC, pageable.getSort().iterator().next().getDirection());
        assertEquals(page, pageable.getPageNumber());
        assertEquals(size, pageable.getPageSize());

        assertEquals(2, response.content().size());
        assertEquals(page, response.pageNumber());
    }
}
