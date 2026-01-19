package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.domain.Token;
import com.zeldev.zel_e_comm.domain.TokenData;
import com.zeldev.zel_e_comm.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import java.util.function.Function;

public interface JwtService {

    String createToken(User user, Function<Token, String> tokenFunction);
    <T> T getTokenData(String token, Function<TokenData, T> tokenDataFunction);
    Boolean validateToken(String jwt);
    ResponseCookie generateJwtCookie(String token);
    String getJwtFromCookie(HttpServletRequest request);
    ResponseCookie generateEmptyCookie();
}
