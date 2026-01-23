package com.zeldev.zel_e_comm.dto.dto_class;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record CartDTO(BigDecimal totalPrice, List<CartItemDTO> products) {
}
