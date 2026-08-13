package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.response.PaymentResponse;
import com.zeldev.zel_e_comm.entity.PaymentEntity;
import com.zeldev.zel_e_comm.enumeration.PaymentType;

import java.util.function.Supplier;

public class PaymentUtils {
    public static PaymentEntity buildPayment(PaymentType paymentType, String pgId, String pgStatus) {
        PaymentEntity payment = partialEntity.get();
        payment.setPgId(pgId);
        payment.setPgStatus(pgStatus);
        payment.setPaymentMethod(paymentType);
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

    private static final Supplier<PaymentEntity> partialEntity = () ->
            PaymentEntity.builder()
                    .pgName(PaymentType.STRIPE.name())
                    .pgResponseMessage("Payment successful")
                    .build();
}
