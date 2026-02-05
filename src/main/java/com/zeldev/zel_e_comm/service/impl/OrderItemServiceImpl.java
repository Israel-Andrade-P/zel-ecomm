package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.OrderItemEntity;
import com.zeldev.zel_e_comm.repository.OrderItemRepository;
import com.zeldev.zel_e_comm.service.OrderItemService;
import com.zeldev.zel_e_comm.util.OrderItemUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Override
    public Set<OrderItemEntity> createOrderItems(Set<CartItemEntity> cartItems, OrderEntity order) {
        return new HashSet<>(orderItemRepository.saveAll(OrderItemUtils.createOrderItems(cartItems, order)));
    }
}
