package com.zeldev.zel_e_comm.filter;

import com.zeldev.zel_e_comm.domain.CustomAuthentication;
import com.zeldev.zel_e_comm.domain.TokenData;
import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.model.User;
import com.zeldev.zel_e_comm.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
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

        try {
            if (jwt != null && jwtService.validateToken(jwt)) {
                User user = jwtService.getTokenData(jwt, TokenData::getUser);
                List<GrantedAuthority> roles = jwtService.getTokenData(jwt, TokenData::getAuthorities);
                UserSecurity userSec = new UserSecurity(user, null);
                var auth = CustomAuthentication.authenticated(userSec, roles);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.debug("Roles from authenticated user: {}", userSec.getAuthorities());
                logger.info("user roles: {}", auth.getAuthorities());
            }
        }catch (Exception exp) {
            logger.error("Couldn't set auth object for user: {}", exp.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}











