package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.dto_class.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/login")
@Tag(name = "Authentication APIs", description = "APIs that manage authentication and sessions")
public class AuthSwaggerController {

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user and returns a JWT token in a cookie"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping
    public void login(
            @RequestBody LoginRequest request
    ) {
        // This method is NEVER called
        throw new UnsupportedOperationException("Swagger-only endpoint");
    }
}
