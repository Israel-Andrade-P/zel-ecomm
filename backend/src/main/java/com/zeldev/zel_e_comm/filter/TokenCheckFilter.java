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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.zeldev.zel_e_comm.constants.Constants.WHITE_LIST;
import static com.zeldev.zel_e_comm.enumeration.UserStatus.ACTIVE;
import static com.zeldev.zel_e_comm.util.UserUtils.fromUserEntity;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCheckFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("TokenCheckFilter called for URI: {}", request.getRequestURI());

        String requestUri = request.getServletPath();

        if (isPublicApi(requestUri)){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt;
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        //accepting header auth option for swagger docs
        if (header != null && header.startsWith("Bearer ")) jwt = jwtService.getJwtFromHeader(request);
        else jwt = jwtService.getJwtFromCookie(request);

        authenticateUserWithToken(request, jwt);

        filterChain.doFilter(request, response);
    }

    private void authenticateUserWithToken(HttpServletRequest request, String jwt) {
        if (jwt == null) {
            log.error("No token found in the request {}", request.getRequestURI());
            return;
        }

        TokenData tokenData = jwtService.getTokenData(jwt);
        if (!tokenData.isValid()) {
            log.error("Token is invalid due to expiration or it's corrupted");
            return;
        }

        UserEntity dbUser = userRepository.findByEmailWithRoles(tokenData.getSubject()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!dbUser.getTokenVersion().equals(tokenData.getTokenVersion()) || dbUser.getStatus() != ACTIVE) return;

        UserSecurity user  = fromUserEntity(dbUser, "");
        var auth = CustomAuthentication.authenticated(user, user.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
        log.info("user roles: {}", auth.getAuthorities());
    }

    private boolean isPublicApi(String path) {
        return Arrays.stream(WHITE_LIST).anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}











