package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.request.OrderRequest;
import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import com.zeldev.zel_e_comm.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Order APIs", description = "APIs that manage orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }

    @GetMapping("/order/get/{order_id}")
    @PreAuthorize("hasRole('ADMIN') || @orderSecurity.isOwner(#orderId)")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("order_id") String orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderResponse(orderId));
    }

    @GetMapping("/admin/orders/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrders());
    }
}
