package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.CartDTO;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(String productId, Integer quantity);

    @Nullable List<CartDTO> getCarts();

    @Nullable CartDTO getCart();

    @Transactional
    @Nullable CartDTO updateQuantity(String productId, Integer quantity);

    void deleteItemFromCart(String productId);
}
