package com.zeldev.zel_e_comm.unittests.jwtservice;

import com.zeldev.zel_e_comm.domain.Token;
import com.zeldev.zel_e_comm.domain.UserSecurity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateTokenTest extends JwtServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a user security and a Function<Token, String>
                    WHEN: createToken is called
                    THEN: generates a access and refresh tokens
                    AND: returns access token
                    """
    )
    void greenPath() {
        UserSecurity user = setup();

        String access = jwtService.createToken(user, Token::getAccess);

        assertNotNull(access);
        assertFalse(access.isBlank());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a user security and a Function<Token, String>
                    WHEN: createToken is called
                    THEN: generates a access and refresh tokens
                    AND: returns refresh token
                    """
    )
    void greenPath2() {
        UserSecurity user = setup();

        String access = jwtService.createToken(user, Token::getRefresh);

        assertNotNull(access);
        assertFalse(access.isBlank());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a user security and a Function<Token, String>
                    WHEN: createToken is called
                    THEN: makes sure the function is being used
                    """
    )
    void greenPath3() {
        UserSecurity user = setup();

        String result = jwtService.createToken(user, token -> "CUSTOM");

        assertEquals("CUSTOM", result);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a user security and a Function<Token, String>
                    WHEN: createToken is called
                    THEN: makes sure it creates a valid token
                    """
    )
    void generatesValidToken() {
        UserSecurity user = setup();

        String token = jwtService.createToken(user, Token::getAccess);

        var data = jwtService.getTokenData(token);

        assertTrue(data.isValid());
        assertEquals("test@gmail", data.getSubject());
        assertEquals("test", data.getUsername());
        assertEquals(1L, data.getUserId());
        assertEquals(0, data.getTokenVersion());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a user security and a Function<Token, String>
                    WHEN: createToken is called with null function value
                    THEN: throws exception
                    """
    )
    void nullPointer() {
        UserSecurity user = setup();

        assertThrows(NullPointerException.class, () -> jwtService.createToken(user, null));
    }

    private UserSecurity setup() {
        UserSecurity user = mock(UserSecurity.class);

        jwtService.setSecret("O94xB3Mg2ayT+SM+8ExVuN+obkkpntc7EwowKBPXFchlNPWY06ggerto53XC8q0H+G56YWrG9LAInLFfjBrKFA==");
        jwtService.setExpiration(86400000L);

        when(user.email()).thenReturn("test@gmail");
        when(user.id()).thenReturn(1L);
        when(user.getUsername()).thenReturn("test");
        when(user.tokenVersion()).thenReturn(0);

        return user;
    }
}
