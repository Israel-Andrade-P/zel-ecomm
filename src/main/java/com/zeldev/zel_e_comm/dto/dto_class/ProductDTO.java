package com.zeldev.zel_e_comm.dto.dto_class;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDTO(
        @Schema(description = "UUID string", example = "dqwidbifbif948194191")
        String publicId,
        @NotNull(message = "Name field is mandatory")
        @NotEmpty(message = "Name field is mandatory")
        @Size(min = 2, max = 50, message = "Name must be between 2 - 50 characters")
        @Schema(example = "Back Scratcher")
        String name,
        @Schema(example = "Perfect for reaching hard spots")
        String description,
        @Schema(example = "cat.txt")
        String image,
        @NotNull(message = "Quantity must be at least 1")
        @PositiveOrZero(message = "Quantity must be at least 1")
        @Schema(description = "must be at least 1", example = "3")
        Integer quantity,
        @Schema(example = "14.89")
        BigDecimal price,
        @Schema(description = "percentage", example = "5")
        Integer discount,
        @Schema(description = "After discount is applied", example = "11.39")
        BigDecimal specialPrice
) {}
