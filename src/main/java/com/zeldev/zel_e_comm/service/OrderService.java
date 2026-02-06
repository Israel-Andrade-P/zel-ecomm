package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.OrderRequest;
import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import org.jspecify.annotations.Nullable;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    @Nullable OrderResponse getOrderResponse(String orderId);

    OrderEntity getOrderEntity(String orderId);
}
