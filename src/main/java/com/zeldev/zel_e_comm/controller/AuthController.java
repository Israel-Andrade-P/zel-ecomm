//package com.zeldev.zel_e_comm.controller;
//
//import com.zeldev.zel_e_comm.dto.dto_class.LoginRequest;
//import com.zeldev.zel_e_comm.dto.response.LoginResponse;
//import com.zeldev.zel_e_comm.jwt.JwtUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class AuthController {
//    private final AuthenticationManager authManager;
//    private final JwtUtils jwtUtils;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        Authentication authentication;
//        try {
//            authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
//
//        } catch (Exception exp) {
//            Map<String, Object> body = new HashMap<>();
//            body.put("message", "Bad Credentials");
//            body.put("status", false);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
//        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String token = jwtUtils.generateTokenFromUsername(userDetails);
//        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
//        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token, userDetails.getUsername(), roles));
//    }
//}
