package com.zeldev.zel_e_comm.dto.dto_class;

import java.math.BigDecimal;

public record OrderItemRequest(
        String productId,
        String productName,
        BigDecimal price,
        Integer quantity,
        BigDecimal discountAmount
) {
}
