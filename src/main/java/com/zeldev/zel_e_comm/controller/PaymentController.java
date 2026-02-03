package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.dto_class.PaymentRequest;
import com.zeldev.zel_e_comm.dto.response.PaymentResponse;
import com.zeldev.zel_e_comm.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/orders/{orderId}")
    public ResponseEntity<PaymentResponse> pay(
            @PathVariable String orderId,
            @RequestBody PaymentRequest request
    ) {
        return ResponseEntity.ok(paymentService.pay(orderId, request));
    }
}
