package com.zeldev.zel_e_comm.webtests.locationcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

public class CreateTest extends LocationControllerBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: LocationDTO in request body
                    WHEN: it hits /locations/add
                    THEN: returns a 201 with a newly created LocationDTO in response body
                    """
    )
    void greenPath() throws JsonProcessingException {
        var location = createLocationDto("New Zealand");

        when(locationService.createLocation(location)).thenReturn(location);

        var result = mockMvc
                .post()
                .uri(BASE_URI.concat("/locations/add"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(location))
                .exchange();

        assertThat(result).hasStatus(CREATED);
        assertThat(result).bodyJson().extractingPath("$.country").isEqualTo(location.country());
        verify(locationService).createLocation(location);
    }
}
