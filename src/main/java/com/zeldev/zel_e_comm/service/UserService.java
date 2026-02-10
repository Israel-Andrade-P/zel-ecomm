package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.response.UserResponse;

public interface UserService {
    String deleteUser(String username);
    UserResponse findUser(String username);
}
