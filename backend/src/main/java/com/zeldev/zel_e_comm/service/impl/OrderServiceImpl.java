package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.request.OrderRequest;
import com.zeldev.zel_e_comm.dto.request.OrderUpdateRequest;
import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.entity.*;
import com.zeldev.zel_e_comm.enumeration.OrderStatus;
import com.zeldev.zel_e_comm.exception.APIException;
import com.zeldev.zel_e_comm.exception.CartIsEmptyException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.repository.OrderRepository;
import com.zeldev.zel_e_comm.service.*;
import com.zeldev.zel_e_comm.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.zeldev.zel_e_comm.enumeration.OrderStatus.PAID;
import static com.zeldev.zel_e_comm.util.OrderUtils.*;
import static com.zeldev.zel_e_comm.util.PageUtils.getPageable;

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

        return toOrderResponse(order);
    }

    @Override
    public OrderResponse getOrderResponse(String orderId) {
        OrderEntity order = getOrderEntity(orderId);
        return toOrderResponse(order);
    }

    @Override
    public OrderEntity getOrderEntity(String orderId) {
        return orderRepository.findByPublicId(orderId).orElseThrow(() -> new ResourceNotFoundException(orderId, "Order"));
    }

    @Override
    public PageResponse<OrderResponse> getOrders(Integer page, Integer size, String sortBy, String sortOrder) {
        Pageable pageDetails = getPageable(page, size, sortBy, sortOrder);

        Page<OrderEntity> orderPage = orderRepository.findAll(pageDetails);

        List<OrderEntity> orders = orderPage.getContent();

        if (orders.isEmpty()) throw new APIException("No orders placed yet :(");

        return buildOrderPageResponse(orderPage, orders);
    }

    @Override
    public void markAsPaid(String orderId, PaymentEntity payment) {
        var order = getOrderEntity(orderId);

        if (order.getStatus() == PAID) return;

        var cart = cartService.getCartByEmail(order.getUser().getEmail());

        order.getOrderItems().forEach(item -> {
            productService.decreaseStock(item.getProduct().getPublicId(), item.getQuantity());
        });

        cart.getCartItems().clear();

        order.setStatus(PAID);
        order.setPayment(payment);
    }

    @Override
    public void updateOrderStatus(String orderId, OrderUpdateRequest request) {
        var parsedStatus = OrderStatus.parse(request.newStatus());

        var order = getOrderEntity(orderId);

        order.setStatus(parsedStatus);
    }
}
