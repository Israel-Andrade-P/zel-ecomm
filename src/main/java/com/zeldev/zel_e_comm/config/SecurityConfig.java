package com.zeldev.zel_e_comm.config;

import com.zeldev.zel_e_comm.filter.AuthFilter;
import com.zeldev.zel_e_comm.filter.TokenCheckFilter;
import com.zeldev.zel_e_comm.security.CustomAccessDeniedHandler;
import com.zeldev.zel_e_comm.security.CustomAuthenticationManager;
import com.zeldev.zel_e_comm.service.AuthService;
import com.zeldev.zel_e_comm.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.zeldev.zel_e_comm.constants.Constants.WHITE_LIST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    //private final AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthFilter authFilter, TokenCheckFilter tokenCheckFilter, CustomAccessDeniedHandler customAccessDeniedHandler) {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(
                req -> req
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/seller/**").hasRole("SELLER")
                        .requestMatchers("/api/v1/manage/products/**").hasAnyRole("ADMIN", "SELLER")
                        .anyRequest().authenticated()
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
        //http.exceptionHandling(exp -> exp.authenticationEntryPoint(authEntryPointJwt));
        http.exceptionHandling(handler -> handler.accessDeniedHandler(customAccessDeniedHandler));
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenCheckFilter, AuthorizationFilter.class);

        return http.build();
    }

    @Bean
    public AuthFilter authFilter(CustomAuthenticationManager manager, AuthService authService, JwtService jwtService) {
        return new AuthFilter(manager, authService, jwtService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenCheckFilter tokenCheckFilter(JwtService jwtService) {
        return new TokenCheckFilter(jwtService);
    }
}
