package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import com.zeldev.zel_e_comm.entity.*;
import com.zeldev.zel_e_comm.enumeration.OrderStatus;

import java.security.SecureRandom;
import java.time.ZoneId;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class OrderUtils {
    public static OrderEntity buildOrder(CartEntity cart, UserEntity user, LocationEntity location) {
        return OrderEntity.builder()
                .publicId(suppliesOrderId.get())
                .status(OrderStatus.PENDING_PAYMENT)
                .user(user)
                .location(location)
                .build();
    }

    public static OrderResponse toOrderResponse(OrderEntity order, Set<OrderItemEntity> orderItems, String email, String locationId) {
        return OrderResponse.builder()
                .orderId(order.getPublicId())
                .userEmail(email)
                .totalPrice(order.getTotalPrice())
                .orderItems(orderItems.stream().map(OrderItemUtils::toOrderItemResponse).collect(Collectors.toSet()))
                .status(order.getStatus())
                .locationPublicId(locationId)
                .createdAt(order.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant())
                .build();
    }

    private static final Supplier<String> suppliesOrderId = () -> {
        String pool = "0123456789ABCDEFGHIKJ";
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 9; i++){
            int randomIndex = random.nextInt(pool.length());
            builder.append(pool.charAt(randomIndex));
        }
        return builder.toString();
    };
}
