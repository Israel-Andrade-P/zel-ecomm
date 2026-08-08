package com.zeldev.zel_e_comm.dto.response;

import lombok.Builder;

@Builder
public record StripeConfirmationResponse(boolean success, String orderId) {
}
