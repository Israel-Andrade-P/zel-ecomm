package com.zeldev.zel_e_comm.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserResponse(
        String username,
        String email,
        String telephone,
        LocalDate dob
) {}
