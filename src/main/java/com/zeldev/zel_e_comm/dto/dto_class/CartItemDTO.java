package com.zeldev.zel_e_comm.dto.dto_class;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CartItemDTO(
        @Schema(example = "dqwidbifbif948194191")
        String productId,
        @Schema(example = "iPhone 15")
        String productName,
        @Schema(example = "2")
        Integer quantity,
        @Schema(example = "499.00")
        BigDecimal price,
        @Schema(description = "percentage", example = "15")
        Integer discount
) {
}
