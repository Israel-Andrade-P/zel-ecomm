package com.zeldev.zel_e_comm.dto.dto_class;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String email,
        @NotBlank
        String password)  {}
