package com.zeldev.zel_e_comm.dto.dto_class;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryDTO(
        @NotNull(message = "Name field can not be empty")
        @NotEmpty(message = "Name field can not be empty")
        @Size(min = 2, max = 20, message = "Category name must be between 2 - 20 characters")
        @Schema(description = "Name of the category to be added, must be unique", example = "Electronics")
        String name
) {}
