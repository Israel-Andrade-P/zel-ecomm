package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.PaymentRequest;
import com.zeldev.zel_e_comm.dto.request.StripeConfirmationRequest;
import com.zeldev.zel_e_comm.dto.request.StripePaymentRequest;
import com.zeldev.zel_e_comm.dto.response.PaymentResponse;
import com.zeldev.zel_e_comm.dto.response.StripeConfirmationResponse;
import com.zeldev.zel_e_comm.dto.response.StripePaymentResponse;
import org.jspecify.annotations.Nullable;

public interface PaymentService {
    PaymentResponse pay(String orderId, PaymentRequest request);
    StripePaymentResponse createPaymentIntent(StripePaymentRequest request);
    StripeConfirmationResponse confirmPayment(String paymentIntentId);
    void handleWebhook(String payload, String signature);
}
