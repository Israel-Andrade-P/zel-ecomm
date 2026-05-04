package com.zeldev.zel_e_comm.unittests.cartservice;

import com.zeldev.zel_e_comm.repository.CartRepository;
import com.zeldev.zel_e_comm.service.CartItemService;
import com.zeldev.zel_e_comm.service.ProductService;
import com.zeldev.zel_e_comm.service.impl.CartServiceImpl;
import com.zeldev.zel_e_comm.util.AuthUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartServiceBaseTest {
    @Mock protected CartRepository cartRepository;
    @Mock protected CartItemService cartItemService;
    @Mock protected ProductService productService;
    @Mock protected AuthUtils authUtils;

    @InjectMocks protected CartServiceImpl cartService;
}
