package com.zeldev.zel_e_comm.dto.response;

import com.zeldev.zel_e_comm.enumeration.OrderStatus;
import com.zeldev.zel_e_comm.enumeration.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Builder
public record OrderResponse(
        String orderId,
        String userEmail,
        Set<OrderItemResponse> orderItems,
        BigDecimal totalPrice,
        OrderStatus status,
        Instant createdAt,
        String locationPublicId,
        PaymentMethod paymentMethod
) {}
