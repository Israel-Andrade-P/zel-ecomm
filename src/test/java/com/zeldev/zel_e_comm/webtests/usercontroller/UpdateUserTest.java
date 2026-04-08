package com.zeldev.zel_e_comm.webtests.usercontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class UpdateUserTest extends UserControllerBaseTest{
    private final String USERNAME = "mike69";
    private final String PATH = String.format("/profile/%s/update", USERNAME);

    @Test
    @DisplayName(
            """
                    WHEN: it hits updateUser endpoint
                    THEN: returns a 200 with a UserResponse in body
                    """
    )
    void greenPath() throws JsonProcessingException {
        var userRequest = new UserRequest("zel22", "zel@gmail", "cheese69", "555", LocalDate.of(1992, 11, 30), null);
        var userResponse = new UserResponse(USERNAME, "mike@gmail", "6767", LocalDate.of(1990, 12, 2));

        when(userService.updateUser(userRequest, USERNAME)).thenReturn(userResponse);

        var response = mockMvc.put()
                .uri(BASE_URI.concat(PATH))
                .contentType("application/json")
                .content(getMapper().writeValueAsString(userRequest))
                .exchange();

        assertThat(response).hasStatus(OK);
        assertThat(response).bodyJson().extractingPath("$.email").isEqualTo(userResponse.email());
        assertThat(response).bodyJson().extractingPath("$.username").isEqualTo(USERNAME);
        assertThat(response).bodyJson().extractingPath("$.telephone").isEqualTo(userResponse.telephone());
        assertThat(response).bodyJson().extractingPath("$.dob").isEqualTo(userResponse.dob().toString());

        verify(userService).updateUser(userRequest, USERNAME);
    }
}
