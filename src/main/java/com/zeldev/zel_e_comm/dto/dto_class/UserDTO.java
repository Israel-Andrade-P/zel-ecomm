package com.zeldev.zel_e_comm.dto.dto_class;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserDTO(
        @NotNull(message = "Username field is mandatory")
        @NotEmpty(message = "Username field is mandatory")
        @Size(min = 2, max = 50, message = "Username must be between 2 - 50 characters")
        String username,
        @Email(message = "Invalid email")
        @NotNull(message = "Email field is mandatory")
        @NotEmpty(message = "Email field is mandatory")
        String email,
        @NotNull(message = "Password field is mandatory")
        @NotEmpty(message = "Password field is mandatory")
        @Size(min = 6, max = 50, message = "Password must be between 6 - 50 characters")
        String password,
        @NotBlank(message = "The field telephone is required")
        @NotEmpty(message = "The field telephone is required")
        String telephone,
        @NotNull(message = "The field Date of Birth is required")
        LocalDate dob,
        LocationDTO location
) {}
