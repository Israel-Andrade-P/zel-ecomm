package com.zeldev.zel_e_comm.dto.dto_class;

import io.swagger.v3.oas.annotations.media.Schema;

public record KeyRequest(
        @Schema(example = "john@gmail.com")
        String email
) {
}
