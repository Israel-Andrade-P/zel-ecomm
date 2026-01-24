package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.KeyRequest;
import com.zeldev.zel_e_comm.dto.dto_class.UserDTO;
import com.zeldev.zel_e_comm.entity.CredentialEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.LoginType;
import com.zeldev.zel_e_comm.model.User;
import org.jspecify.annotations.Nullable;

public interface AuthService {

    UserDTO createUser(UserDTO user);
    void verifyAccount(String key);
    //User getUserByEmail(String email);
    void updateLoginAttempt(String email, LoginType loginType);
    CredentialEntity getCredentialByUserId(Long userId);
    UserEntity getUserEntityByEmail(String email);
    User getUserByEmail(String email);

    @Nullable String getNewKey(KeyRequest keyRequest);
    //LocationEntity getLocationByUserId(Long userId);
}
