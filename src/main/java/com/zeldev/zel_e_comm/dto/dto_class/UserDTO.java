package com.zeldev.zel_e_comm.dto.dto_class;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserDTO(
        @NotNull(message = "Username field is mandatory")
        @NotEmpty(message = "Username field is mandatory")
        @Size(min = 2, max = 50, message = "Username must be between 2 - 50 characters")
        @Schema(example = "dave412")
        String username,
        @Email(message = "Invalid email")
        @NotNull(message = "Email field is mandatory")
        @NotEmpty(message = "Email field is mandatory")
        @Schema(example = "dave@gmail.com")
        String email,
        @NotNull(message = "Password field is mandatory")
        @NotEmpty(message = "Password field is mandatory")
        @Size(min = 6, max = 50, message = "Password must be between 6 - 50 characters")
        @Schema(example = "cheese876")
        String password,
        @NotBlank(message = "The field telephone is required")
        @NotEmpty(message = "The field telephone is required")
        @Schema(example = "555932984")
        String telephone,
        @NotNull(message = "The field Date of Birth is required")
        @Schema(example = "1993-12-11")
        LocalDate dob,
        LocationDTO location
) {}
