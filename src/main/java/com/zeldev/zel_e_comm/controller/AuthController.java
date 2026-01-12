package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.dto_class.LoginRequest;
import com.zeldev.zel_e_comm.dto.dto_class.UserDTO;
import com.zeldev.zel_e_comm.dto.response.LoginResponse;
import com.zeldev.zel_e_comm.jwt.JwtUtils;
import com.zeldev.zel_e_comm.security.UserSecurity;
import com.zeldev.zel_e_comm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        } catch (Exception exp) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Bad Credentials");
            body.put("status", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserSecurity) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new LoginResponse(userDetails.getUsername(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(request));
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        return ResponseEntity.status(HttpStatus.OK).body(authentication.getName());
    }

    @GetMapping("/user")
    public ResponseEntity<LoginResponse> getUserDetails(Authentication authentication) {
        UserDetails userDetails = (UserSecurity) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(userDetails.getUsername(), roles));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie emptyCookie = jwtUtils.generateEmptyCookie();
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, emptyCookie.toString()).body("Signed out!");
    }
}
