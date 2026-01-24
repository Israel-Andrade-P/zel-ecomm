package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.dto_class.CartDTO;
import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;

public class CartUtils {
    public static CartEntity buildCart(UserEntity user) {
        return CartEntity.builder()
                .user(user)
                .build();
    }

    public static CartDTO toDTO(CartEntity cart) {
        return CartDTO.builder()
                .totalPrice(cart.getTotalPrice())
                .products(cart.getCartItems().stream().map(CartItemUtils::toDTO).toList())
                .build();
    }
}
