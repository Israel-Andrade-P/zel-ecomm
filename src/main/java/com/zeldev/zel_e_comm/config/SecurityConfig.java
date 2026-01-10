//package com.zeldev.zel_e_comm.config;
//
//import com.zeldev.zel_e_comm.jwt.AuthEntryPointJwt;
//import com.zeldev.zel_e_comm.jwt.JwtUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final AuthEntryPointJwt authEntryPointJwt;
//    private final JwtUtils jwtUtils;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.authorizeHttpRequests(
//                req -> req.requestMatchers("/api/login").permitAll()
//                        .anyRequest().authenticated()
//        );
//        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
//        http.exceptionHandling(exp -> exp.authenticationEntryPoint(authEntryPointJwt));
//        //http.addFilterBefore(authTokenFilter(), AuthorizationFilter.class);
//
//        return http.build();
//    }
//
////    @Bean
////    public UserDetailsService userDetailsService() {
////        UserDetails user = User.withUsername("user")
////                .password(passwordEncoder().encode("password"))
////                .roles("USER")
////                .build();
////
////        UserDetails admin = User.withUsername("admin")
////                .password(passwordEncoder().encode("adminpass"))
////                .roles("ADMIN")
////                .build();
////
////        return new InMemoryUserDetailsManager(user, admin);
////    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) {
//        return builder.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Bean
////    public AuthTokenFilter authTokenFilter() {
////        return new AuthTokenFilter(jwtUtils, userDetailsService());
////    }
//}
