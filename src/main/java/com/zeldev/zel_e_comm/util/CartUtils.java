package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.dto_class.CartDTO;
import com.zeldev.zel_e_comm.entity.CartEntity;

public class CartUtils {
    public static CartEntity buildCart() {
        return CartEntity.builder()
                .user(AuthUtils.getLoggedInUser())
                .build();
    }

    public static CartDTO toDTO(CartEntity cart) {
        return CartDTO.builder()
                .totalPrice(cart.getTotalPrice())
                .products(cart.getCartItems().stream().map(CartItemUtils::toDTO).toList())
                .build();
    }
}
