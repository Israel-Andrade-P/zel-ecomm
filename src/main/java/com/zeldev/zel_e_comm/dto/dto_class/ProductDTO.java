package com.zeldev.zel_e_comm.dto.dto_class;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDTO(
        String publicId,
        @NotNull(message = "Name field is mandatory")
        @NotEmpty(message = "Name field is mandatory")
        @Size(min = 2, max = 50, message = "Name must be between 2 - 50 characters")
        String name,
        String description,
        String image,
        @NotNull(message = "Quantity must be at least 1")
        @PositiveOrZero(message = "Quantity must be at least 1")
        Integer quantity,
        BigDecimal price,
        Integer discount,
        BigDecimal specialPrice
) {}
