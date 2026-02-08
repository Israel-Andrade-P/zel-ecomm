package com.zeldev.zel_e_comm.dto.dto_class;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        @Schema(example = "karen@gmail.com")
        String email,
        @NotBlank
        @Schema(example = "iluvcheese45")
        String password)  {}
