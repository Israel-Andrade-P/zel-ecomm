package com.zeldev.zel_e_comm.dto.dto_class;

import com.zeldev.zel_e_comm.enumeration.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;

public record PaymentRequest(
        @Schema(example = "CREDIT_CARD")
        PaymentMethod paymentMethod
) {}
