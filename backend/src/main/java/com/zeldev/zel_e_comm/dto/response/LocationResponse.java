package com.zeldev.zel_e_comm.dto.response;

import lombok.Builder;

@Builder
public record LocationResponse(
        String publicId,
        String country,
        String city,
        String street,
        String zipCode
) {}
