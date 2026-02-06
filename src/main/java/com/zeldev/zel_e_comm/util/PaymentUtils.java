package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.dto_class.PaymentRequest;
import com.zeldev.zel_e_comm.dto.response.PaymentResponse;
import com.zeldev.zel_e_comm.entity.PaymentEntity;

import java.util.function.Supplier;

public class PaymentUtils {
    public static PaymentEntity buildPayment(PaymentRequest request) {
        PaymentEntity payment = parcialEntity.get();
        payment.setPaymentMethod(request.paymentMethod());
        return payment;
    }

    public static PaymentResponse toPaymentResponse(PaymentEntity entity) {
        return PaymentResponse.builder()
                .paymentMethod(entity.getPaymentMethod())
                .pgId(entity.getPgId())
                .pgName(entity.getPgName())
                .pgStatus(entity.getPgStatus())
                .pgResponseMessage(entity.getPgResponseMessage())
                .build();
    }

    private static Supplier<PaymentEntity> parcialEntity = () ->
            PaymentEntity.builder()
                    .pgId("42069")
                    .pgName("FakePicPay")
                    .pgStatus("PAID")
                    .pgResponseMessage("Payment successful")
                    .build();
}
