package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.OrderEntity;

import java.util.Set;

public interface OrderItemService {
    void createOrderItems(Set<CartItemEntity> cartItems, OrderEntity order);
}
