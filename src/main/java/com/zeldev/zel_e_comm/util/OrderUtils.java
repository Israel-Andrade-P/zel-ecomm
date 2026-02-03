package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.OrderStatus;

import java.security.SecureRandom;
import java.util.function.Supplier;

public class OrderUtils {
    public OrderEntity buildOrder(CartEntity cart, UserEntity user, LocationEntity location) {
        return OrderEntity.builder()
                .publicId(suppliesOrderId.get())
                .status(OrderStatus.PENDING_PAYMENT)
                .user(user)
                .location(location)
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
