package com.zeldev.zel_e_comm.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt.secret")
@Getter
@Setter
public class JwtConfig {
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.cookie}")
    private String jwtCookie;
}
