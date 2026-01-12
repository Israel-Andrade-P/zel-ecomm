package com.zeldev.zel_e_comm.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.cookie}")
    private String jwtCookie;

//    public String getJwtFromHeader(HttpServletRequest request) {
//        String header = request.getHeader(AUTHORIZATION);
//        logger.debug("Authorization Header {}", header);
//        if (header == null || !header.startsWith("Bearer ")) return null;
//        return header.substring(7);
//    }

    public String getJwtFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie == null) return null;
        return cookie.getValue();
    }

    public ResponseCookie generateJwtCookie(UserDetails userDetails) {
        String jwt = generateTokenFromUsername(userDetails.getUsername());
        return ResponseCookie.from(jwtCookie, jwt)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(false)
                .build();
    }

    public ResponseCookie generateEmptyCookie() {
        return ResponseCookie.from(jwtCookie, "")
                .path("/api")
                .build();
    }

    public String generateTokenFromUsername(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime() + jwtExpirationMs)))
                .signWith(key())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException exp) {
            logger.error("invalid jwt token, {}", exp.getMessage());
        }
        catch (ExpiredJwtException exp) {
            logger.error("jwt token is expired, {}", exp.getMessage());
        }catch (UnsupportedJwtException exp) {
            logger.error("jwt token is unsupported, {}", exp.getMessage());
        }catch (IllegalArgumentException exp) {
            logger.error("jwt claims string is empty, {}", exp.getMessage());
        }
        return false;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}















