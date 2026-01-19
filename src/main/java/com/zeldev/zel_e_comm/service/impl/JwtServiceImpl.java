package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.domain.Token;
import com.zeldev.zel_e_comm.domain.TokenData;
import com.zeldev.zel_e_comm.enumeration.TokenType;
import com.zeldev.zel_e_comm.model.User;
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
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.zeldev.zel_e_comm.constants.Constants.*;
import static com.zeldev.zel_e_comm.enumeration.TokenType.ACCESS;
import static com.zeldev.zel_e_comm.enumeration.TokenType.REFRESH;
import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static java.time.Instant.now;
import static java.util.Date.from;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

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

    private final BiFunction<User, TokenType, String> buildToken = (user, type) ->
            Objects.equals(type, ACCESS) ? builder.get()
                    .subject(user.getEmail())
                    .claim(ROLES, user.getRoles().toString())
                    .expiration(from(now().plusSeconds(getExpiration())))
                    .compact() : builder.get()
                    .subject(user.getEmail())
                    .expiration(from(now().plusSeconds(getExpiration())))
                    .compact();

    private final Function<String, Claims> getClaims = token ->
            Jwts.parser()
                    .verifyWith(key.get())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

    private final Function<String, String> subject = token -> getClaimsValue(token, Claims::getSubject);

    private <T> T getClaimsValue(String token, Function<Claims, T> claims){
        return getClaims.andThen(claims).apply(token);
    }

    public Function<String, List<GrantedAuthority>> authorities = token ->
            commaSeparatedStringToAuthorityList(new StringJoiner(AUTHORITY_DELIMITER)
                    .add(ROLE_PREFIX + getClaims.apply(token).get(ROLES, String.class)).toString());

    @Override
    public String createToken(User user, Function<Token, String> tokenFunction) {
        var token = Token.builder().access(buildToken.apply(user, ACCESS)).refresh(buildToken.apply(user, REFRESH)).build();
        return tokenFunction.apply(token);
    }

    @Override
    public <T> T getTokenData(String token, Function<TokenData, T> tokenDataFunction) {
        return tokenDataFunction.apply(
                TokenData.builder()
                        .valid(validateToken(token))
                        .authorities(authorities.apply(token))
                        .claims(getClaims.apply(token))
                        .user(userService.getUserByEmail(subject.apply(token)))
                        .build()
        );
    }
    @Override
    public Boolean validateToken(String jwt) {
        return getClaims.apply(jwt).getAudience().contains(ZEL_DEV_INC);
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
}
