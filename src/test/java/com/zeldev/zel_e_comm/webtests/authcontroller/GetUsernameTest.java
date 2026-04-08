package com.zeldev.zel_e_comm.webtests.authcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GetUsernameTest extends AuthControllerBaseTest{

    @Test
    @DisplayName(
            """
                    WHEN: it hits getUsername endpoint
                    THEN: returns a 200 with username in body
                    """
    )
    void greenPath() throws UnsupportedEncodingException {
        when(authUtils.getLoggedInUsername()).thenReturn("zel_dev");

        var response = mockMvc.get()
                .uri(BASE_URI.concat("/username"))
                .exchange();

        assertThat(response).hasStatus(HttpStatus.OK);
        assertThat(response.getResponse().getContentAsString()).contains("zel_dev");

        verify(authUtils).getLoggedInUsername();
    }
}
