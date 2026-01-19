package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.domain.Response;
import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.dto.dto_class.KeyRequest;
import com.zeldev.zel_e_comm.dto.dto_class.UserDTO;
import com.zeldev.zel_e_comm.dto.response.LoginResponse;
import com.zeldev.zel_e_comm.service.AuthService;
import com.zeldev.zel_e_comm.service.JwtService;
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
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        Authentication authentication;
//        try {
//            authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
//
//        } catch (Exception exp) {
//            Map<String, Object> body = new HashMap<>();
//            body.put("message", "Bad Credentials");
//            body.put("status", false);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
//        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetails userDetails = (UserSecurity) authentication.getPrincipal();
//        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
//        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
//        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new LoginResponse(userDetails.getUsername(), roles));
//    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.createUser(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<Response> verifyAccount(@RequestParam(name = "key") String key, HttpServletRequest request){
        authService.verifyAccount(key);
        return ResponseEntity.ok().body(getResponse(request,emptyMap(),ACCOUNT_VERIFIED_MESSAGE, OK));
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
