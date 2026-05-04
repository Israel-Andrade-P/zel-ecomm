package com.zeldev.zel_e_comm.webtests.locationcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class UpdateTest extends LocationControllerBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a LocationDTO and a public id
                    WHEN: it hits /locations/update/{id}
                    THEN: returns a 200 with an updated LocationDTO in response body
                    """
    )
    void greenPath() throws JsonProcessingException {
        var publicId = "locationId";
        var location = createLocationDto("China");

        when(locationService.updateLocation(location, publicId)).thenReturn(location);

        var result = mockMvc
                .put()
                .uri(BASE_URI.concat(String.format("/locations/update/%s", publicId)))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(location))
                .exchange();

        assertThat(result).hasStatus(OK);
        assertThat(result).bodyJson().extractingPath("$.country").isEqualTo(location.country());
        verify(locationService).updateLocation(location, publicId);
    }
}
