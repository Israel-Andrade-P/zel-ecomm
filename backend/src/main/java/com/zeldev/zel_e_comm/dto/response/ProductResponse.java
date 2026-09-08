package com.zeldev.zel_e_comm.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String productId,
        String productName,
        String description,
        String image,
        Integer quantity,
        BigDecimal price,
        Integer discount,
        BigDecimal specialPrice
) {}
