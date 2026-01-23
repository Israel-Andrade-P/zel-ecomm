package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.CartDTO;

public interface CartService {
    CartDTO addProductToCart(String productId, Integer quantity);
}
