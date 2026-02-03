package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.PaymentRequest;
import com.zeldev.zel_e_comm.dto.response.PaymentResponse;
import com.zeldev.zel_e_comm.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    @Override
    public @Nullable PaymentResponse pay(String orderId, PaymentRequest request) {
        return null;
    }
}
