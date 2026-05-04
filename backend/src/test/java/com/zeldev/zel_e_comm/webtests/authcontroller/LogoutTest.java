package com.zeldev.zel_e_comm.webtests.authcontroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogoutTest extends AuthControllerBaseTest{

    @Test
    @DisplayName(
            """
                    WHEN: it hits logout endpoint
                    THEN: returns a 200 and a empty cookie that overrides the jwt cookie
                    """
    )
    void greenPath() throws UnsupportedEncodingException {
        when(jwtService.generateEmptyCookie()).thenReturn(
                ResponseCookie.from("zel-inc", "")
                        .path("/api")
                        .build()
        );

        var response = mockMvc.post()
                .uri(BASE_URI.concat("/logout"))
                .exchange();

        assertThat(response).hasStatus(HttpStatus.OK);
        assertThat(response.getResponse().getContentAsString()).isEqualTo("Signed out!");

        String setCookie = response.getResponse().getHeader("Set-Cookie");
        assertThat(setCookie).isNotNull();

        assertThat(setCookie).contains("zel-inc=");
        assertThat(setCookie).contains("Path=/api");

        verify(jwtService).generateEmptyCookie();
    }
}
