package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.OrderItemEntity;

import java.util.Set;

public interface OrderItemService {
    Set<OrderItemEntity> createOrderItems(Set<CartItemEntity> cartItems, OrderEntity order);
}
