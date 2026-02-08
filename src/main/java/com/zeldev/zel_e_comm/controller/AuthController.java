package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.domain.Response;
import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.dto.dto_class.KeyRequest;
import com.zeldev.zel_e_comm.dto.dto_class.UserDTO;
import com.zeldev.zel_e_comm.dto.response.LoginResponse;
import com.zeldev.zel_e_comm.service.AuthService;
import com.zeldev.zel_e_comm.service.JwtService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

import static com.zeldev.zel_e_comm.constants.Constants.ACCOUNT_VERIFIED_MESSAGE;
import static com.zeldev.zel_e_comm.util.RequestUtils.getResponse;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication APIs", description = "APIs that manage authentication and sessions")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.createUser(request));
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
        return ResponseEntity.status(HttpStatus.OK).body(authService.getNewKey(request));
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        return ResponseEntity.status(HttpStatus.OK).body(authentication.getName());
    }

    @GetMapping("/user")
    public ResponseEntity<LoginResponse> getUserDetails(Authentication authentication) {
        UserDetails userDetails = (UserSecurity) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(userDetails.getUsername(), roles));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie emptyCookie = jwtService.generateEmptyCookie();
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, emptyCookie.toString()).body("Signed out!");
    }
}
