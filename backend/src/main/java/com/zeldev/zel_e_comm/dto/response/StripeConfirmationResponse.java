package com.zeldev.zel_e_comm.dto.response;

public record StripeConfirmationResponse(boolean success, String orderId) {
}
