package com.zeldev.zel_e_comm.webtests.locationcontroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;

public class DeleteTest extends LocationControllerBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN:a public id
                    WHEN: it hits /locations/delete/{id}
                    THEN: returns a 200 with a Response in response body
                    """
    )
    void greenPath() {
        var publicId = "locationId";

        var result = mockMvc
                .delete()
                .uri(BASE_URI.concat(String.format("/locations/delete/%s", publicId)))
                .exchange();

        assertThat(result).hasStatus(OK);
        assertThat(result).bodyJson().extractingPath("$.message").isEqualTo("Location deleted");
        verify(locationService).deleteLocation(publicId);
    }
}
