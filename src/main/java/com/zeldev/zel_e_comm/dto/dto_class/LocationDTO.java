package com.zeldev.zel_e_comm.dto.dto_class;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record LocationDTO(
        @NotEmpty(message = "The field country is required")
        @NotBlank(message = "The field country is required")
        @Schema(example = "Norway")
        String country,
        @NotEmpty(message = "The field city is required")
        @NotBlank(message = "The field city is required")
        @Schema(example = "Osaka city")
        String city,
        @NotEmpty(message = "The field street is required")
        @NotBlank(message = "The field street is required")
        @Schema(example = "69th Ave")
        String street,
        @NotEmpty(message = "The field zip code is required")
        @NotBlank(message = "The field zip code is required")
        @Schema(example = "555372")
        String zipCode
) {}
