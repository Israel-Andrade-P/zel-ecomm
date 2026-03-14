package com.zeldev.zel_e_comm.unittests.cartitemservice;

import com.zeldev.zel_e_comm.repository.CartItemRepository;
import com.zeldev.zel_e_comm.service.ProductService;
import com.zeldev.zel_e_comm.service.impl.CartItemServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceBaseTest {
    @Mock protected CartItemRepository cartItemRepository;
    @Mock protected ProductService productService;

    @InjectMocks protected CartItemServiceImpl cartItemService;
}
