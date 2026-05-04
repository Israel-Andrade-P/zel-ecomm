package com.zeldev.zel_e_comm.unittests.productservice;

import com.zeldev.zel_e_comm.config.AppConfig;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.repository.ProductRepository;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import com.zeldev.zel_e_comm.service.FileService;
import com.zeldev.zel_e_comm.service.impl.ProductServiceImpl;
import com.zeldev.zel_e_comm.util.AuthUtils;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProductServiceBaseTest {
    @Mock protected ProductRepository productRepository;
    @Mock protected CategoryService categoryService;
    @Mock protected UserRepository userRepository;
    @Mock protected FileService fileService;
    @Mock protected AuthUtils authUtils;
    @Mock protected AppConfig appConfig;

    @InjectMocks protected ProductServiceImpl productService;

    protected ProductEntity createProduct(String name) {
        ProductEntity product = new ProductEntity();
        product.setPublicId(UUID.randomUUID());
        product.setName(name);
        product.setDescription("old azz phone");
        product.setQuantity(2);
        product.setPrice(BigDecimal.valueOf(2000));
        product.setDiscount(2);
        product.setImage("random_image");
        return product;
    }

    protected void assertPageable(
            Pageable pageable,
            int page,
            int size,
            String sort,
            Sort.Direction direction
    ) {
        assertEquals(page, pageable.getPageNumber());
        assertEquals(size, pageable.getPageSize());

        Sort.Order order = pageable.getSort().getOrderFor(sort);
        assertEquals(direction, order.getDirection());
    }

    static Stream<Arguments> specialPriceCases() {
        return Stream.of(
                Arguments.of(Named.of("price changed", BigDecimal.valueOf(4000)), null, true),
                Arguments.of(null, Named.of("discount changed", 10), true),
                Arguments.of(BigDecimal.valueOf(4000), 10, true),
                Arguments.of(null, null, false)
        );
    }

    static Stream<Arguments> fieldCases() {
        return Stream.of(
                Arguments.of("iPhone17", "cool phone bruh", 2),
                Arguments.of(" ", "cool phone bruh", null),
                Arguments.of("iPhone17", null, 2),
                Arguments.of("", " ", 2),
                Arguments.of(null, null, null)
        );
    }
}
