package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.PaymentRequest;
import com.zeldev.zel_e_comm.dto.request.StripePaymentRequest;
import com.zeldev.zel_e_comm.dto.response.PaymentResponse;
import com.zeldev.zel_e_comm.dto.response.StripePaymentResponse;

public interface PaymentService {
    PaymentResponse pay(String orderId, PaymentRequest request);
    StripePaymentResponse createPaymentIntent(StripePaymentRequest request);
}
