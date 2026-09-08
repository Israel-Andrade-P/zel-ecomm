package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.OrderRequest;
import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.PaymentEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    @Nullable OrderResponse getOrderResponse(String orderId);

    OrderEntity getOrderEntity(String orderId);

    @Nullable PageResponse<OrderResponse> getOrders(Integer page, Integer size, String sortBy, String sortOrder);

    void markAsPaid(String orderId, PaymentEntity payment);
}
