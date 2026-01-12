package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.UserDTO;

public interface UserService {
    UserDTO registerUser(UserDTO request);
    UserDTO deleteUser(String email);
    UserDTO findUser(String email);
}
