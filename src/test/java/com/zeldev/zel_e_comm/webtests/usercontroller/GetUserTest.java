package com.zeldev.zel_e_comm.webtests.usercontroller;

import com.zeldev.zel_e_comm.dto.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class GetUserTest extends UserControllerBaseTest{
    private final String USERNAME = "mike69";
    private final String PATH = String.format("/profile/%s", USERNAME);

    @Test
    @DisplayName(
            """
                    WHEN: it hits getUser endpoint
                    THEN: returns a 200 with a UserResponse in body
                    """
    )
    void greenPath() {
        var userResponse = new UserResponse(USERNAME, "mike@gmail", "6767", LocalDate.of(1990, 12, 2));

        when(userService.findUser(USERNAME)).thenReturn(userResponse);

        var response = mockMvc.get()
                .uri(BASE_URI.concat(PATH))
                .exchange();

        assertThat(response).hasStatus(OK);
        assertThat(response).bodyJson().extractingPath("$.email").isEqualTo(userResponse.email());
        assertThat(response).bodyJson().extractingPath("$.username").isEqualTo(USERNAME);
        assertThat(response).bodyJson().extractingPath("$.telephone").isEqualTo(userResponse.telephone());
        assertThat(response).bodyJson().extractingPath("$.dob").isEqualTo(userResponse.dob().toString());

        verify(userService).findUser(USERNAME);
    }
}
