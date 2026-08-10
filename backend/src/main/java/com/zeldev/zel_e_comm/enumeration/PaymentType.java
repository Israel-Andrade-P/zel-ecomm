package com.zeldev.zel_e_comm.enumeration;

import com.zeldev.zel_e_comm.exception.UnsupportedPaymentMethodException;

import java.util.Arrays;

public enum PaymentType {
    CREDIT_CARD,
    DEBIT_CARD,
    PAYPAL,
    STRIPE,
    BANK_TRANSFER,
    CASH_ON_DELIVERY,
    GIFT_CARD,
    STORE_CREDIT,
    PIX,
    BOLETO;

    public static PaymentType findMethod(String paymentMethod) {
        return Arrays.stream(PaymentType.values())
                .filter(payMeth -> payMeth.name().equals(paymentMethod))
                .findFirst()
                .orElseThrow(() -> new UnsupportedPaymentMethodException("This payment method is not supported"));
    }
}
