package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.domain.CustomAuthentication;
import com.zeldev.zel_e_comm.domain.Response;
import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.dto.request.KeyRequest;
import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.LoginResponse;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.service.AuthService;
import com.zeldev.zel_e_comm.service.JwtService;
import com.zeldev.zel_e_comm.util.AuthUtils;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.zeldev.zel_e_comm.constants.Constants.ACCOUNT_VERIFIED_MESSAGE;
import static com.zeldev.zel_e_comm.util.RequestUtils.getResponse;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication APIs", description = "APIs that manage authentication and sessions")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final AuthUtils authUtils;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest request) {
        return ResponseEntity.status(CREATED).body(authService.createUser(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<Response> verifyAccount(@Parameter(description = "Confirmation key sent to user's email")
                                                  @RequestParam(name = "key") String key,
                                                  HttpServletRequest request) {
        authService.verifyAccount(key);
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), ACCOUNT_VERIFIED_MESSAGE, OK));
    }

    @PostMapping("/new_key")
    public ResponseEntity<String> getNewKey(@RequestBody KeyRequest request) {
        return ResponseEntity.status(OK).body(authService.getNewKey(request));
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername() {
        return ResponseEntity.status(OK).body(authUtils.getLoggedInUsername());
    }

    @GetMapping("/user")
    public ResponseEntity<LoginResponse> getUserDetails() {
        CustomAuthentication auth = authUtils.getAuthObj();
        return ResponseEntity.status(OK).body(new LoginResponse(auth.getEmail(), auth.getAuthorities()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie emptyCookie = jwtService.generateEmptyCookie();
        return ResponseEntity.status(OK).header(HttpHeaders.SET_COOKIE, emptyCookie.toString()).body("Signed out!");
    }
}
