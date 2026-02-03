package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.dto_class.OrderRequest;
import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        return null;
    }
}
