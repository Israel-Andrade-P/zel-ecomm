package com.zeldev.zel_e_comm.webtests.authcontroller;

import com.zeldev.zel_e_comm.domain.CustomAuthentication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GetUserDetailsTest extends AuthControllerBaseTest{

    @Test
    @DisplayName(
            """
                    WHEN: it hits getUserDetails endpoint
                    THEN: returns a 200 with user email and authorities in body
                    """
    )
    void greenPath() {
        var auth = mock(CustomAuthentication.class);

        when(authUtils.getAuthObj()).thenReturn(auth);
        when(auth.getEmail()).thenReturn("test@gmail.com");
        when(auth.getAuthorities()).thenReturn(List.of(() -> "ROLE_USER"));

        var response = mockMvc.get()
                .uri(BASE_URI.concat("/user"))
                .exchange();

        assertThat(response).hasStatus(HttpStatus.OK);
        assertThat(response).bodyJson().extractingPath("$.email").isEqualTo("test@gmail.com");
        assertThat(response).bodyJson().extractingPath("$.roles[0].authority").isEqualTo("ROLE_USER");

        verify(authUtils).getAuthObj();
    }
}
