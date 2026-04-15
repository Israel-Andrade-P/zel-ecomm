package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.KeyRequest;
import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import com.zeldev.zel_e_comm.entity.CredentialEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.LoginType;
import org.jspecify.annotations.Nullable;

public interface AuthService {

    UserResponse createUser(UserRequest user);
    void verifyAccount(String key);
    //User getUserByEmail(String email);
    void updateLoginAttempt(String email, LoginType loginType);
    CredentialEntity getCredentialByUserId(Long userId);
    UserEntity getUserEntityByEmail(String email);
    UserEntity getUserWithRoles(String email);

    @Nullable String getNewKey(KeyRequest keyRequest);
    //LocationEntity getLocationByUserId(Long userId);
}
