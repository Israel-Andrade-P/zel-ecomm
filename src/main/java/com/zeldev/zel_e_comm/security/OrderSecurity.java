package com.zeldev.zel_e_comm.security;

import com.zeldev.zel_e_comm.repository.OrderRepository;
import com.zeldev.zel_e_comm.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderSecurity {
    private final OrderRepository orderRepository;
    private final AuthUtils authUtils;

    public boolean isOwner(String orderId) {
        if (orderId == null) return false;
        return orderRepository.existsByIdAndUserEmail(orderId, authUtils.getLoggedInEmail());
    }
}
