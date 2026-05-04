package com.zeldev.zel_e_comm.webtests.authcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeldev.zel_e_comm.dto.request.LocationDTO;
import com.zeldev.zel_e_comm.dto.request.UserRequest;
import com.zeldev.zel_e_comm.dto.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

public class RegisterTest extends AuthControllerBaseTest{
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = getMapper();
    }


    @Test
    @DisplayName(
            """
                    GIVEN: a UserRequest
                    WHEN: it hits register endpoint
                    THEN: returns a 201
                    """
    )
    void greenPath() throws JsonProcessingException {
        UserRequest request = new UserRequest(
                "zel",
                "zel@gmail",
                "cheese",
                "555",
                LocalDate.of(1992, 11, 30),
                new LocationDTO("Norway", "Viking City", "68th", "4343"));
        UserResponse response = new UserResponse(
                "zel",
                "zel@gmail",
                "555",
                LocalDate.of(1992, 11, 30)
                );

        when(authService.createUser(request)).thenReturn(response);

        mockMvc.post().uri(BASE_URI.concat("/register"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
                .exchange()
                .assertThat()
                .hasStatus(CREATED)
                .bodyJson()
                .isEqualTo(objectMapper.writeValueAsString(response));

        verify(authService).createUser(request);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a UserRequest with invalid fields
                    WHEN: it hits register endpoint
                    THEN: returns a 400
                    """
    )
    void redPath() throws JsonProcessingException {
        UserRequest request = new UserRequest(
                "z",
                "zelgmail",
                "12",
                "",
                null,
                new LocationDTO("Norway", "Viking City", "68th", "4343"));

        mockMvc.post().uri(BASE_URI.concat("/register"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
                .exchange()
                .assertThat()
                .hasStatus(BAD_REQUEST);

        verify(authService, never()).createUser(request);
    }
}
