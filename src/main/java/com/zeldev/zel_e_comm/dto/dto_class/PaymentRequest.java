package com.zeldev.zel_e_comm.dto.dto_class;

import com.zeldev.zel_e_comm.enumeration.PaymentMethod;

public record PaymentRequest(
        PaymentMethod paymentMethod
) {}
