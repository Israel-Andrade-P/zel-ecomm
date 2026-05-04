package com.zeldev.zel_e_comm.unittests.jwtservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;

import static org.junit.jupiter.api.Assertions.*;

public class CookieTests extends JwtServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a string token
                    WHEN: generateJwtCookie is called
                    THEN: generates a cookie
                    AND: returns response cookie
                    """
    )
    void generateJwtCookie() {
        jwtService.setJwtCookie("ecomm-cookie");

        ResponseCookie cookie = jwtService.generateJwtCookie("my-token");

        assertEquals("ecomm-cookie", cookie.getName());
        assertEquals("my-token", cookie.getValue());
        assertEquals("/api", cookie.getPath());
        assertEquals(86400, cookie.getMaxAge().getSeconds());
        assertFalse(cookie.isHttpOnly());
    }

    @Test
    @DisplayName(
            """
                    WHEN: generateEmptyCookie is called
                    THEN: generates a cookie empty
                    AND: returns empty response cookie
                    """
    )
    void generateEmpty() {
        jwtService.setJwtCookie("ecomm-cookie");

        ResponseCookie cookie = jwtService.generateEmptyCookie();

        assertEquals("ecomm-cookie", cookie.getName());
        assertTrue(cookie.getValue().isEmpty());
        assertEquals("/api", cookie.getPath());
    }
}
