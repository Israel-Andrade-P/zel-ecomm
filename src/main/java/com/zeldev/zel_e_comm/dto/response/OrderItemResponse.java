package com.zeldev.zel_e_comm.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponse(
        String productName,
        BigDecimal price,
        Integer quantity,
        BigDecimal discountAmount
) {}
