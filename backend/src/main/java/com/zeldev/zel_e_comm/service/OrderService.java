package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.OrderRequest;
import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.PaymentEntity;
import com.zeldev.zel_e_comm.enumeration.PaymentType;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    @Nullable OrderResponse getOrderResponse(String orderId);

    OrderEntity getOrderEntity(String orderId);

    @Nullable List<OrderResponse> getOrders();

    void markAsPaid(String orderId, PaymentEntity payment);
}
