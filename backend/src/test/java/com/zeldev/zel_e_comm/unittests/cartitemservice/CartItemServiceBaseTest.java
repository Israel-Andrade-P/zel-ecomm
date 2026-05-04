package com.zeldev.zel_e_comm.unittests.cartitemservice;

import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.repository.CartItemRepository;
import com.zeldev.zel_e_comm.service.ProductService;
import com.zeldev.zel_e_comm.service.impl.CartItemServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceBaseTest {
    @Mock protected CartItemRepository cartItemRepository;
    @Mock protected ProductService productService;

    @InjectMocks protected CartItemServiceImpl cartItemService;

    public ProductEntity createProduct() {
        ProductEntity product = new ProductEntity();
        product.setPublicId(UUID.randomUUID());
        product.setName("TV");
        product.setSpecialPrice(BigDecimal.valueOf(69.99));
        product.setDiscount(3);
        return product;
    }
}
