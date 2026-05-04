package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.domain.Token;
import com.zeldev.zel_e_comm.domain.TokenData;
import com.zeldev.zel_e_comm.domain.UserSecurity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import java.util.function.Function;

public interface JwtService {

    String createToken(UserSecurity user, Function<Token, String> tokenFunction);
    TokenData getTokenData(String token);
    ResponseCookie generateJwtCookie(String token);
    String getJwtFromCookie(HttpServletRequest request);
    String getJwtFromHeader(HttpServletRequest request);
    ResponseCookie generateEmptyCookie();
}
