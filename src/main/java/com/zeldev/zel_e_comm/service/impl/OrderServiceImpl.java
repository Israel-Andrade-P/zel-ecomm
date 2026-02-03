package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.CartDTO;
import com.zeldev.zel_e_comm.dto.dto_class.OrderRequest;
import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.exception.CartIsEmptyException;
import com.zeldev.zel_e_comm.repository.OrderRepository;
import com.zeldev.zel_e_comm.service.CartService;
import com.zeldev.zel_e_comm.service.LocationService;
import com.zeldev.zel_e_comm.service.OrderService;
import com.zeldev.zel_e_comm.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final LocationService locationService;
    private final AuthUtils authUtils;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        CartDTO cart = cartService.getCart();
        if (cart.products().isEmpty()) throw new CartIsEmptyException("Cart is empty");

        UserEntity user = authUtils.getLoggedInUser();
        LocationEntity location = locationService.getByPublicId(UUID.fromString(request.locationPublicId()));


        return null;
    }
}
