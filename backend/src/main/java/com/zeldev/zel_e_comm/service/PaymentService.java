package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.StripePaymentRequest;
import com.zeldev.zel_e_comm.dto.response.StripeConfirmationResponse;
import com.zeldev.zel_e_comm.dto.response.StripePaymentResponse;
import com.zeldev.zel_e_comm.entity.PaymentEntity;
import com.zeldev.zel_e_comm.enumeration.PaymentType;

public interface PaymentService {
//    PaymentResponse pay(String orderId, PaymentRequest request);
    PaymentEntity persistPaymentEntity(PaymentType paymentType);
    StripePaymentResponse createPaymentIntent(StripePaymentRequest request);
    StripeConfirmationResponse confirmPayment(String paymentIntentId);
    void handleWebhook(String payload, String signature);
}
