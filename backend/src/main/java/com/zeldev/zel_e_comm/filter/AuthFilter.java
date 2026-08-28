package com.zeldev.zel_e_comm.filter;

import com.zeldev.zel_e_comm.domain.CustomAuthentication;
import com.zeldev.zel_e_comm.domain.Token;
import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.dto.request.LoginRequest;
import com.zeldev.zel_e_comm.dto.response.LoginResponse;
import com.zeldev.zel_e_comm.security.CustomAuthenticationManager;
import com.zeldev.zel_e_comm.service.AuthService;
import com.zeldev.zel_e_comm.service.JwtService;
import com.zeldev.zel_e_comm.util.ApiResponseWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.List;

import static com.zeldev.zel_e_comm.constants.Constants.LOGIN_PATH;
import static com.zeldev.zel_e_comm.enumeration.LoginType.LOGIN_ATTEMPT;
import static com.zeldev.zel_e_comm.enumeration.LoginType.LOGIN_SUCCESS;

@Slf4j
public class AuthFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthService authService;
    private final JwtService jwtService;
    private final ApiResponseWriter responseWriter;
    private final JsonMapper jsonMapper;

    public AuthFilter(CustomAuthenticationManager manager, AuthService authService, JwtService jwtService, ApiResponseWriter responseWriter, JsonMapper jsonMapper) {
        super(
                request -> LOGIN_PATH.equals(request.getServletPath())
                        && HttpMethod.POST.matches(request.getMethod()), manager
                );
        this.authService = authService;
        this.jwtService = jwtService;
        this.responseWriter = responseWriter;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        try{
            LoginRequest user = jsonMapper/*.configure(AUTO_CLOSE_SOURCE, true)*/.readValue(request.getInputStream(), LoginRequest.class);
            authService.updateLoginAttempt(user.email(), LOGIN_ATTEMPT);
            CustomAuthentication authentication = CustomAuthentication.unauthenticated(user.email(), user.password());
            return getAuthenticationManager().authenticate(authentication);
        }catch (Exception e){
            log.error(e.getMessage());
            responseWriter.handleErrorResponse(request, response, e);
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        UserSecurity user = (UserSecurity) authentication.getPrincipal();
        authService.updateLoginAttempt(user.email(), LOGIN_SUCCESS);
        String token = jwtService.createToken(user, Token::getAccess);
        List<String> userRoles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        var loginResponse = new LoginResponse(user.email(), user.getUsername(), userRoles);
        response.addHeader(HttpHeaders.SET_COOKIE, jwtService.generateJwtCookie(token).toString());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        var out = response.getOutputStream();
        jsonMapper.writeValue(out, loginResponse);
        out.flush();
    }
}
