package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.request.OrderRequest;
import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import com.zeldev.zel_e_comm.entity.*;
import com.zeldev.zel_e_comm.exception.CartIsEmptyException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.repository.OrderRepository;
import com.zeldev.zel_e_comm.service.*;
import com.zeldev.zel_e_comm.util.AuthUtils;
import com.zeldev.zel_e_comm.util.OrderUtils;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.zeldev.zel_e_comm.util.OrderUtils.buildOrder;
import static com.zeldev.zel_e_comm.util.OrderUtils.toOrderResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final LocationService locationService;
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final AuthUtils authUtils;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        UserEntity user = authUtils.getLoggedInUser();
        Set<CartItemEntity> cartItems = cartService.getCartByEmail(user.getEmail()).getCartItems();
        if (cartItems.isEmpty()) throw new CartIsEmptyException("Cart is empty");

        LocationEntity location = locationService.getByPublicIdAndUserEmail(request.locationPublicId(), user.getEmail());

        OrderEntity order = buildOrder(user, location);
        orderRepository.save(order);

        orderItemService.createOrderItems(cartItems, order);

        //update product stock
        cartItems.forEach(item -> {
            productService.decreaseStock(item.getProduct().getPublicId(), item.getQuantity());
        });

        //clear cart
        cartItems.clear();

        //send back OrderResponse
        return toOrderResponse(order);
    }

    @Override
    public @Nullable OrderResponse getOrderResponse(String orderId) {
        OrderEntity order = getOrderEntity(orderId);
        return toOrderResponse(order);
    }

    @Override
    public OrderEntity getOrderEntity(String orderId) {
        return orderRepository.findByPublicId(orderId).orElseThrow(() -> new ResourceNotFoundException(orderId, "Order"));
    }

    @Override
    public @Nullable List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream().map(OrderUtils::toOrderResponse).toList();
    }
}
