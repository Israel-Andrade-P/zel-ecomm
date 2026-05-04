package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.response.OrderItemResponse;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.OrderItemEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderItemUtils {
    public static List<OrderItemEntity> createOrderItems(Set<CartItemEntity> cartItems, OrderEntity order) {
        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (CartItemEntity cartItem : cartItems) {
            ProductEntity product = cartItem.getProduct();
            BigDecimal discountAmount = product.getPrice()
                    .multiply(BigDecimal.valueOf(product.getDiscount()))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .productName(product.getName())
                    .price(cartItem.getPrice())
                    .quantity(cartItem.getQuantity())
                    .discountAmount(discountAmount)
                    .product(product)
                    .order(order)
                    .build();
            order.addItem(orderItem);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public static OrderItemResponse toOrderItemResponse(OrderItemEntity orderItem) {
        return OrderItemResponse.builder()
                .productName(orderItem.getProductName())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .discountAmount(orderItem.getDiscountAmount())
                .build();
    }
}
