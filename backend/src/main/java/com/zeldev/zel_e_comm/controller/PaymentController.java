package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.request.PaymentRequest;
import com.zeldev.zel_e_comm.dto.request.StripeConfirmationRequest;
import com.zeldev.zel_e_comm.dto.request.StripePaymentRequest;
import com.zeldev.zel_e_comm.dto.response.PaymentResponse;
import com.zeldev.zel_e_comm.dto.response.StripeConfirmationResponse;
import com.zeldev.zel_e_comm.dto.response.StripePaymentResponse;
import com.zeldev.zel_e_comm.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment APIs", description = "APIs that manage payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/orders/{orderId}")
    public ResponseEntity<PaymentResponse> pay(
            @PathVariable String orderId,
            @RequestBody PaymentRequest request
    ) {
        return ResponseEntity.ok(paymentService.pay(orderId, request));
    }

    @PostMapping("/stripe/client-secret")
    public ResponseEntity<StripePaymentResponse> createPaymentIntent(@RequestBody StripePaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPaymentIntent(request));
    }

    @GetMapping("/stripe/confirm")
    public ResponseEntity<StripeConfirmationResponse> confirmPayment(@RequestParam(name = "payment_intent") String paymentIntentId) {
        log.info("In controller...");
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.confirmPayment(paymentIntentId));
    }

    @PostMapping("/stripe/webhook")
        public ResponseEntity<Void> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String signature) {
        log.info("Stripe webhook endpoint hit!");
            paymentService.handleWebhook(payload, signature);
            return ResponseEntity.ok().build();
        }
}
