package com.zeldev.zel_e_comm.webtests.authcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeldev.zel_e_comm.dto.request.KeyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class GetNewKeyTest extends AuthControllerBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a KeyRequest
                    WHEN: it hits getNewKey endpoint
                    THEN: returns a 200
                    """
    )
    void greenPath() throws JsonProcessingException, UnsupportedEncodingException {
        KeyRequest keyRequest = new KeyRequest("zel@gmail");
        ObjectMapper objectMapper = getMapper();
        String response = "New key generated!";

        when(authService.getNewKey(keyRequest)).thenReturn(response);

        var result = mockMvc.post()
                .uri(BASE_URI.concat("/new_key"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(keyRequest))
                .exchange();

        assertThat(result).hasStatus(OK);
        assertThat(result.getResponse().getContentAsString()).contains(response);

        verify(authService).getNewKey(keyRequest);
    }
}
