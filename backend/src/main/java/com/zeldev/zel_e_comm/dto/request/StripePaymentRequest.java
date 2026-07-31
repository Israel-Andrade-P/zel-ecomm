package com.zeldev.zel_e_comm.dto.request;

import lombok.Builder;

@Builder
public record StripePaymentRequest(String orderId) {
}
