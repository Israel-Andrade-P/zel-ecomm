package com.zeldev.zel_e_comm.dto.response;

import com.zeldev.zel_e_comm.enumeration.PaymentMethod;
import lombok.Builder;

@Builder
public record PaymentResponse(
        PaymentMethod paymentMethod,
        String pgId,
        String pgStatus,
        String pgResponseMessage,
        String pgName
) {}
