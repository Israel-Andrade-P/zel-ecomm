package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.domain.Response;
import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.service.UserService;
import com.zeldev.zel_e_comm.util.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile/{username}")
    @PreAuthorize("principal.getUsername().equals(#username) || hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUser(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(username));
    }

    //todo create new dto for updates with some validation
    @PutMapping("/profile/{username}/update")
    @PreAuthorize("principal.getUsername().equals(#username) || hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest request, @PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(request, username));
    }

    @DeleteMapping("/profile/{username}/delete")
    @PreAuthorize("principal.getUsername().equals(#username) || hasRole('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable("username") String username, HttpServletRequest request) {
        String message = userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(RequestUtils.getResponse(request, Collections.emptyMap(), message, HttpStatus.OK));
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }
}
