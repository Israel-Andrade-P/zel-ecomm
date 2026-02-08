package com.zeldev.zel_e_comm.dto.dto_class;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record CartDTO(
        @Schema(description = "Cart's current total price", example = "2999.00")
        BigDecimal totalPrice,
        @Schema(description = "All cart items listed, that are currently in cart")
        List<CartItemDTO> products) {
}
