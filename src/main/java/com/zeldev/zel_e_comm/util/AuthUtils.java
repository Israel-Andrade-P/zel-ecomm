package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.domain.CustomAuthentication;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {
    private final AuthService authService;

    public String getLoggedInEmail() {
        CustomAuthentication loggedUser = (CustomAuthentication) getAuthObj();
        return loggedUser.getEmail();
    }

    public UserEntity getLoggedInUser() {
        return authService.getUserEntityByEmail(getLoggedInEmail());
    }

    private Authentication getAuthObj() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("No authentication found in security context");
        }

        if (!(auth instanceof CustomAuthentication customAuth)) {
            throw new IllegalStateException(
                    "Expected CustomAuthentication but found " + auth.getClass().getSimpleName()
            );
        }
        return customAuth;
    }
}
