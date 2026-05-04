package com.zeldev.zel_e_comm.webtests.authcontroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.zeldev.zel_e_comm.constants.Constants.ACCOUNT_VERIFIED_MESSAGE;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;

public class VerifyAccountTest extends AuthControllerBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a query param key string
                    WHEN: it hits verifyAccount endpoint
                    THEN: returns a Response 200
                    """
    )
    void greenPath() {
        String myKey = "473782";
        mockMvc.get()
                .uri(BASE_URI.concat("/verify"))
                .param("key", myKey)
                .exchange()
                .assertThat()
                .hasStatus(OK)
                .bodyJson()
                .extractingPath("$.message")
                .isEqualTo(ACCOUNT_VERIFIED_MESSAGE);

        verify(authService).verifyAccount(myKey);
    }
}
