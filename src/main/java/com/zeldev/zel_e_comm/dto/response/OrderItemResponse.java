package com.zeldev.zel_e_comm.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(
        String productName,
        BigDecimal price,
        Integer quantity,
        BigDecimal discountAmount
) {}
