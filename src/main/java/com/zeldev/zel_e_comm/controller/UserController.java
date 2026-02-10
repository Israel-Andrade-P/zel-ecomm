package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.domain.Response;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.service.UserService;
import com.zeldev.zel_e_comm.util.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(username));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Response> deleteUser(@PathVariable("username") String username, HttpServletRequest request) {
        String message = userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(RequestUtils.getResponse(request, Collections.emptyMap(), message, HttpStatus.OK));
    }
}
