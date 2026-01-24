package com.zeldev.zel_e_comm.dto.dto_class;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CartItemDTO(
        String productId,
        String productName,
        Integer quantity,
        BigDecimal price,
        Integer discount
) {
}
