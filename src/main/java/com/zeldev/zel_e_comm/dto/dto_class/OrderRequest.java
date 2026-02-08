package com.zeldev.zel_e_comm.dto.dto_class;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrderRequest(
        @Schema(description = "UUID string", example = "djasidauiq938491")
        String locationPublicId
) {}
