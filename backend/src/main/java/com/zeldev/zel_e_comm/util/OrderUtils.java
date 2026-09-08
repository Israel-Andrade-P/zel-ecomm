package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.OrderStatus;
import org.springframework.data.domain.Page;

import java.security.SecureRandom;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class OrderUtils {
    public static OrderEntity buildOrder(UserEntity user, LocationEntity location) {
        return OrderEntity.builder()
                .publicId(suppliesOrderId.get())
                .status(OrderStatus.PENDING_PAYMENT)
                .user(user)
                .location(location)
                .build();
    }

    public static OrderResponse toOrderResponse(OrderEntity order) {
        return OrderResponse.builder()
                .orderId(order.getPublicId())
                .userEmail(order.getUser().getEmail())
                .totalPrice(order.getTotalPrice())
                .orderItems(order.getOrderItems().stream().map(OrderItemUtils::toOrderItemResponse).collect(Collectors.toSet()))
                .status(order.getStatus())
                .locationPublicId(order.getLocation().getPublicId().toString())
                //.paymentMethod(order.getPayment().getPaymentMethod())
                .createdAt(order.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant())
                .build();
    }

    public static PageResponse<OrderResponse> buildOrderPageResponse(Page<OrderEntity> page, List<OrderEntity> orders) {
        return PageResponse.<OrderResponse>builder()
                .content(orders.stream().map(OrderUtils::toOrderResponse).toList())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .lastPage(page.isLast())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
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
