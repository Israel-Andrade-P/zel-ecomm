package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.OrderRequest;
import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import org.jspecify.annotations.Nullable;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    @Nullable OrderResponse getById(String orderId);
}
