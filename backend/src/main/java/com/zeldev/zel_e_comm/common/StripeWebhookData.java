package com.zeldev.zel_e_comm.common;

import com.zeldev.zel_e_comm.enumeration.PaymentType;
import lombok.Builder;

@Builder
public record StripeWebhookData(String orderId, PaymentType paymentType) {
}
