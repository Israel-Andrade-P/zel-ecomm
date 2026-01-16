package com.zeldev.zel_e_comm.dto.dto_class;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record LocationDTO(
        @NotEmpty(message = "The field country is required")
        @NotBlank(message = "The field country is required")
        String country,
        @NotEmpty(message = "The field city is required")
        @NotBlank(message = "The field city is required")
        String city,
        @NotEmpty(message = "The field street is required")
        @NotBlank(message = "The field street is required")
        String street,
        @NotEmpty(message = "The field zip code is required")
        @NotBlank(message = "The field zip code is required")
        String zipCode
) {}
