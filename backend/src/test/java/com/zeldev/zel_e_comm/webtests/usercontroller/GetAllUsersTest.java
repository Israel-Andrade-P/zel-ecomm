package com.zeldev.zel_e_comm.webtests.usercontroller;

import com.zeldev.zel_e_comm.dto.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class GetAllUsersTest extends UserControllerBaseTest{
    private final String PATH = "/admin/users";

    @Test
    @DisplayName(
            """
                    WHEN: it hits getAllUsers endpoint
                    THEN: returns a 200 with a List of UserResponse in body
                    """
    )
    void greenPath() {
        var users = List.of(
                new UserResponse("karen44", "karen@gmail", "4545", LocalDate.of(1800, 4, 12)),
                new UserResponse("jake55", "jake@gmail", "3232", LocalDate.of(1999, 8, 25))
        );

        when(userService.getAllUsers()).thenReturn(users);

        var response = mockMvc.get()
                .uri(BASE_URI.concat(PATH))
                .exchange();

        assertThat(response).hasStatus(OK);
        assertThat(response).bodyJson().extractingPath("$[0].username").isEqualTo("karen44");
        assertThat(response).bodyJson().extractingPath("$[0].email").isEqualTo("karen@gmail");
        assertThat(response).bodyJson().extractingPath("$[0].telephone").isEqualTo("4545");
        assertThat(response).bodyJson().extractingPath("$[1].username").isEqualTo("jake55");
        assertThat(response).bodyJson().extractingPath("$[1].email").isEqualTo("jake@gmail");
        assertThat(response).bodyJson().extractingPath("$[1].telephone").isEqualTo("3232");

        verify(userService).getAllUsers();
    }
}
