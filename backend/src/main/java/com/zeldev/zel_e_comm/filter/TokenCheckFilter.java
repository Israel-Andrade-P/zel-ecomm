package com.zeldev.zel_e_comm.filter;

import com.zeldev.zel_e_comm.domain.CustomAuthentication;
import com.zeldev.zel_e_comm.domain.TokenData;
import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.exception.UserNotFoundException;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.zeldev.zel_e_comm.enumeration.UserStatus.ACTIVE;
import static com.zeldev.zel_e_comm.util.UserUtils.fromUserEntity;

@Component
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(TokenCheckFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")){
            filterChain.doFilter(request, response);
            return;
        }
        logger.debug("Custom filter called for URI: {}", request.getRequestURI());

        String jwt;
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        //accepting header auth option for swagger docs
        if (header != null && header.startsWith("Bearer ")) jwt = jwtService.getJwtFromHeader(request);
        else jwt = jwtService.getJwtFromCookie(request);

        authenticateUserWithToken(request, jwt);

        filterChain.doFilter(request, response);
    }

    private void authenticateUserWithToken(HttpServletRequest request, String jwt) {
        if (jwt == null) return;

        TokenData tokenData = jwtService.getTokenData(jwt);
        if (!tokenData.isValid()) return;

        UserEntity DBUser = userRepository.findByEmailWithRoles(tokenData.getSubject()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!DBUser.getTokenVersion().equals(tokenData.getTokenVersion()) || DBUser.getStatus() != ACTIVE) return;

        UserSecurity user  = fromUserEntity(DBUser, "");
        var auth = CustomAuthentication.authenticated(user, user.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
        logger.info("user roles: {}", auth.getAuthorities());
    }
}











