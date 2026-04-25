package com.zeldev.zel_e_comm.webtests.locationcontroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class FindAllTest extends LocationControllerBaseTest{

    @Test
    @DisplayName(
            """
                    WHEN: it hits /admin/locations/all
                    THEN: returns a 200 with a list of LocationDTO in response body
                    """
    )
    void greenPath() {
        var location1 = createLocationDto("China");
        var location2 = createLocationDto("Japan");
        var locations = List.of(location1, location2);

        when(locationService.getAll()).thenReturn(locations);

        var result = mockMvc
                .get()
                .uri(BASE_URI.concat("/admin/locations/all"))
                .exchange();

        assertThat(result).hasStatus(OK);
        assertThat(result).bodyJson().extractingPath("$[0].country").isEqualTo(locations.getFirst().country());
        assertThat(result).bodyJson().extractingPath("$[1].country").isEqualTo(locations.getLast().country());
        verify(locationService).getAll();
    }
}
