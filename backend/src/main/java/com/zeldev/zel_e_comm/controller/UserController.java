package com.zeldev.zel_e_comm.controller;

import static com.zeldev.zel_e_comm.constants.Constants.PAGE_NUMBER;
import static com.zeldev.zel_e_comm.constants.Constants.PAGE_SIZE;
import static com.zeldev.zel_e_comm.constants.Constants.SORT_DIR;
import static com.zeldev.zel_e_comm.constants.Constants.SORT_USER_BY;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zeldev.zel_e_comm.domain.Response;
import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.service.UserService;
import com.zeldev.zel_e_comm.util.ApiResponseWriter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final ApiResponseWriter responseWriter;

  @GetMapping("/profile/{username}")
  @PreAuthorize("principal.getUsername().equals(#username) || hasRole('ADMIN')")
  public ResponseEntity<UserResponse> getUser(@PathVariable("username") String username) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(username));
  }

  // todo create new dto for updates with some validation
  @PutMapping("/profile/{username}/update")
  @PreAuthorize("principal.getUsername().equals(#username) || hasRole('ADMIN')")
  public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest request,
      @PathVariable("username") String username) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(request, username));
  }

  @DeleteMapping("/profile/{username}/delete")
  @PreAuthorize("principal.getUsername().equals(#username) || hasRole('ADMIN')")
  public ResponseEntity<Response> deleteUser(@PathVariable("username") String username, HttpServletRequest request) {
    String message = userService.deleteUser(username);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseWriter.getResponse(request, Collections.emptyMap(), message, HttpStatus.OK));
  }

  @GetMapping("/admin/users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<PageResponse<UserResponse>> getAllUsers(
      @RequestParam(name = "page", defaultValue = PAGE_NUMBER, required = false) Integer page,
      @RequestParam(name = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
      @RequestParam(name = "sortBy", defaultValue = SORT_USER_BY, required = false) String sortBy,
      @RequestParam(name = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers(page, size, sortBy, sortOrder));
  }

  @GetMapping("/admin/sellers")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<PageResponse<UserResponse>> getAllSellers(
      @RequestParam(name = "page", defaultValue = PAGE_NUMBER, required = false) Integer page,
      @RequestParam(name = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
      @RequestParam(name = "sortBy", defaultValue = SORT_USER_BY, required = false) String sortBy,
      @RequestParam(name = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.getAllSellers(page, size, sortBy, sortOrder));
  }
}
