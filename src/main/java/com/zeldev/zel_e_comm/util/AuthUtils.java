package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
    public static String getLoggedInEmail() {
        var loggedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static UserEntity getLoggedInUser() {
        return null;
    }
}
