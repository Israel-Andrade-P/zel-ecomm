package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import org.jspecify.annotations.Nullable;


public interface UserService {
    String deleteUser(String username);
    UserResponse findUser(String username);
    @Nullable UserResponse updateUser(UserRequest request, String username);
    PageResponse<UserResponse> getAllUsers(Integer page, Integer size, String sortBy, String sortOrder);
    PageResponse<UserResponse> getAllSellers(Integer page, Integer size, String sortBy, String sortOrder);
}
