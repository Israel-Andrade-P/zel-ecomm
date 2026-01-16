package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.UserDTO;
import com.zeldev.zel_e_comm.entity.CredentialEntity;
import com.zeldev.zel_e_comm.enumeration.LoginType;

public interface AuthService {

    UserDTO createUser(UserDTO user);
    void verifyAccount(String key);
    //User getUserByEmail(String email);
    void updateLoginAttempt(String email, LoginType loginType);
    CredentialEntity getCredentialByUserId(Long userId);
    //LocationEntity getLocationByUserId(Long userId);
}
