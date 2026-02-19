package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.domain.Token;
import com.zeldev.zel_e_comm.domain.TokenData;
import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.enumeration.TokenType;
import com.zeldev.zel_e_comm.security.JwtConfig;
import com.zeldev.zel_e_comm.service.AuthService;
import com.zeldev.zel_e_comm.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.zeldev.zel_e_comm.constants.Constants.ZEL_DEV_INC;
import static com.zeldev.zel_e_comm.enumeration.TokenType.ACCESS;
import static com.zeldev.zel_e_comm.enumeration.TokenType.REFRESH;
import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static java.time.Instant.now;
import static java.util.Date.from;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl extends JwtConfig implements JwtService {
    private final AuthService userService;

    private final Supplier<SecretKey> key = () -> Keys.hmacShaKeyFor(Decoders.BASE64.decode(getSecret()));

    private final Supplier<JwtBuilder> builder = () ->
            Jwts.builder()
                    .header().add(Map.of(TYPE, JWT_TYPE))
                    .and()
                    .audience().add(ZEL_DEV_INC)
                    .and()
                    .id(UUID.randomUUID().toString())
                    .issuedAt(from(now()))
                    .notBefore(new Date())
                    .signWith(key.get(), Jwts.SIG.HS512);

    private final BiFunction<UserSecurity, TokenType, String> buildToken = (user, type) ->
            Objects.equals(type, ACCESS) ? builder.get()
                    .subject(user.email())
                    .claim("id", user.id())
                    .claim("username", user.getUsername())
                    .claim("token_version", user.tokenVersion())
                    .expiration(from(now().plusSeconds(getExpiration())))
                    .compact() : builder.get()
                    .subject(user.email())
                    .expiration(from(now().plusSeconds(getExpiration())))
                    .compact();

    private final Function<String, Claims> getClaims = token ->
            Jwts.parser()
                    .verifyWith(key.get())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

    private final Function<Claims, Integer> getTokenVersion = claims -> claims.get("token_version", Integer.class);

    private final Function<Claims, String> getUsername = claims -> claims.get("username", String.class);

    private final Function<Claims, Long> getId = claims -> claims.get("id", Long.class);

    @Override
    public String createToken(UserSecurity user, Function<Token, String> tokenFunction) {
        var token = Token.builder().access(buildToken.apply(user, ACCESS)).refresh(buildToken.apply(user, REFRESH)).build();
        return tokenFunction.apply(token);
    }

    @Override
    public TokenData getTokenData(String token) {
        try {
            Claims claims = getClaims.apply(token);
            return TokenData.builder()
                    .valid(claims.getAudience().contains(ZEL_DEV_INC))
                    .subject(claims.getSubject())
                    .username(getUsername.apply(claims))
                    .userId(getId.apply(claims))
                    .tokenVersion(getTokenVersion.apply(claims))
                    .build();
        }catch (Exception e) {
            return TokenData.builder()
                    .valid(false)
                    .build();
        }
    }

    @Override
    public ResponseCookie generateJwtCookie(String token) {
        return ResponseCookie.from(getJwtCookie(), token)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(false)
                .build();
    }

    @Override
    public ResponseCookie generateEmptyCookie() {
        return ResponseCookie.from(getJwtCookie(), "")
                .path("/api")
                .build();
    }

    @Override
    public String getJwtFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, getJwtCookie());
        if (cookie == null) return null;
        return cookie.getValue();
    }

    @Override
    public String getJwtFromHeader(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
    }
}
