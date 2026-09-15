package com.zeldev.zel_e_comm.enumeration;

import com.zeldev.zel_e_comm.exception.InvalidOrderStatusException;

import java.util.Locale;

public enum OrderStatus {
    PENDING_PAYMENT,
    PAID,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELED,
    RETURNED,
    FAILED;

    public static OrderStatus parse(String data) {
        if (data == null || data.isBlank()) throw new InvalidOrderStatusException("No order status provided");

        try {
            return OrderStatus.valueOf(data.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderStatusException("Invalid order status: " + data);
        }
    }
}
