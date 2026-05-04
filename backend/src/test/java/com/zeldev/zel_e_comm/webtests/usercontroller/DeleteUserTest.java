package com.zeldev.zel_e_comm.webtests.usercontroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class DeleteUserTest extends UserControllerBaseTest{
    private final String USERNAME = "mike69";
    private final String PATH = String.format("/profile/%s/delete", USERNAME);

    @Test
    @DisplayName(
            """
                    WHEN: it hits deleteUser endpoint
                    THEN: returns a 200 with a Response in body
                    """
    )
    void greenPath() {
        when(userService.deleteUser(USERNAME)).thenReturn("User deleted");

        var response = mockMvc.delete()
                .uri(BASE_URI.concat(PATH))
                .exchange();

        assertThat(response).hasStatus(OK);
        assertThat(response).bodyJson().extractingPath("$.message").isEqualTo("User deleted");
        assertThat(response).bodyJson().extractingPath("$.code").isEqualTo(200);
        assertThat(response).bodyJson().extractingPath("$.path").isEqualTo(BASE_URI.concat(PATH));

        verify(userService).deleteUser(USERNAME);
    }
}
