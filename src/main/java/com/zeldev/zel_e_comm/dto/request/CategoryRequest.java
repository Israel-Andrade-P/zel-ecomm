package com.zeldev.zel_e_comm.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotNull(message = "Name field can not be empty")
        @NotEmpty(message = "Name field can not be empty")
        @Size(min = 2, max = 20, message = "Category name must be between 2 - 20 characters")
        String name
) {
}
