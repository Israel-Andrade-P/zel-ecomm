package com.zeldev.zel_e_comm.unittests.orderservice;

import com.zeldev.zel_e_comm.repository.OrderRepository;
import com.zeldev.zel_e_comm.service.CartService;
import com.zeldev.zel_e_comm.service.LocationService;
import com.zeldev.zel_e_comm.service.OrderItemService;
import com.zeldev.zel_e_comm.service.ProductService;
import com.zeldev.zel_e_comm.service.impl.OrderServiceImpl;
import com.zeldev.zel_e_comm.util.AuthUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceBaseTest {
    @Mock protected OrderRepository orderRepository;
    @Mock protected CartService cartService;
    @Mock protected LocationService locationService;
    @Mock protected OrderItemService orderItemService;
    @Mock protected ProductService productService;
    @Mock AuthUtils authUtils;

    @InjectMocks protected OrderServiceImpl orderService;
}
