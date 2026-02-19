package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface UserService {
    String deleteUser(String username);
    UserResponse findUser(String username);
    @Nullable UserResponse updateUser(UserRequest request, String username);
    @Nullable List<UserResponse> getAllUsers();
}
