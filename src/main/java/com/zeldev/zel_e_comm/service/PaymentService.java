package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.PaymentRequest;
import com.zeldev.zel_e_comm.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse pay(String orderId, PaymentRequest request);
}
