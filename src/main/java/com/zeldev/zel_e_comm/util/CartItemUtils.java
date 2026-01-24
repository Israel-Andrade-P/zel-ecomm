package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.dto_class.CartItemDTO;
import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;

public class CartItemUtils {
    public static CartItemEntity buildCartItem(ProductEntity product, CartEntity cart, Integer quantity) {
        var item = CartItemEntity.builder()
                .price(product.getSpecialPrice())
                .quantity(quantity)
                .discount(product.getDiscount())
                .product(product)
                .cart(cart)
                .build();
        cart.addItem(item);
        return item;
    }

    public static CartItemDTO toDTO(CartItemEntity itemEntity) {
        return CartItemDTO.builder()
                .productId(itemEntity.getProduct().getPublicId().toString())
                .productName(itemEntity.getProduct().getName())
                .price(itemEntity.getPrice())
                .quantity(itemEntity.getQuantity())
                .discount(itemEntity.getDiscount())
                .build();
    }
}
