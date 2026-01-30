package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface CartItemService {
    CartItemEntity createItem(ProductEntity product, CartEntity cart, Integer quantity);
    void checkCartItemByCartAndProduct(CartEntity cart, ProductEntity product);
    CartItemEntity getCartItemByCartAndProduct(Long cartId, UUID productId);
    void updateQuantity(CartEntity cart, ProductEntity product, Integer quantity);
    void validateQuantity(Integer requestedQuantity, Integer inStock);
    List<CartItemEntity> findActiveCartItemsByProductId(UUID productId);
    void deleteByProductPublicId(UUID productId);
}
