package com.zeldev.zel_e_comm.unittests.jwtservice;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExtractJwtMethodsTest extends JwtServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a servlet request
                    WHEN: getJwtFromCookie is called
                    THEN: grab jwt cookie
                    AND: return its value
                    """
    )
    void greenPath() {
        String token = "my-token";
        jwtService.setJwtCookie("ecomm-cookie");
        HttpServletRequest request = mock(HttpServletRequest.class);

        Cookie cookie = new Cookie(jwtService.getJwtCookie(), token);
        Cookie[] cookies = {cookie};

        when(request.getCookies()).thenReturn(cookies);

        var value = jwtService.getJwtFromCookie(request);

        assertEquals(token, value);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a servlet request
                    WHEN: getJwtFromCookie is called
                    THEN: cookie isn't there
                    AND: return null
                    """
    )
    void returnNull() {
        jwtService.setJwtCookie("ecomm-cookie");
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getCookies()).thenReturn(null);

        var value = jwtService.getJwtFromCookie(request);

        assertNull(value);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a servlet request
                    WHEN: getJwtFromHeader is called
                    THEN: get auth header
                    AND: return jwt
                    """
    )
    void greenPathHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn("Bearer my-token");

        String result = jwtService.getJwtFromHeader(request);

        assertEquals("my-token", result);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a servlet request
                    WHEN: getJwtFromHeader is called
                    THEN: auth header not there
                    AND: throws exception
                    """
    )
    void redPathHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn(null);

        assertThrows(NullPointerException.class, () -> jwtService.getJwtFromHeader(request));
    }
}
