package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;

public interface CartItemService {
    CartItemEntity createItem(ProductEntity product, CartEntity cart, Integer quantity);
    void checkCartItemByCartAndProduct(CartEntity cart, ProductEntity product);
}
