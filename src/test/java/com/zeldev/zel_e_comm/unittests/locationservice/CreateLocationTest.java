package com.zeldev.zel_e_comm.unittests.locationservice;

import com.zeldev.zel_e_comm.dto.request.LocationDTO;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreateLocationTest extends LocationServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a location DTO 
                    WHEN: createLocation is called
                    THEN: builds and persists a new location entity
                    """
    )
    void greenPath() {
        LocationDTO locationDTO = new LocationDTO("China", "Shanghai", "69th", "555");
        UserEntity user = new UserEntity();
        user.setUsername("zeldev");

        when(authUtils.getLoggedInUser()).thenReturn(user);
        when(locationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var response = locationService.createLocation(locationDTO);

        ArgumentCaptor<LocationEntity> captor = ArgumentCaptor.forClass(LocationEntity.class);

        verify(locationRepository).save(captor.capture());

        LocationEntity location = captor.getValue();

        assertEquals(locationDTO.country(), location.getCountry());
        assertEquals(locationDTO.city(), location.getCity());
        assertEquals(locationDTO.street(), location.getStreet());
        assertEquals(locationDTO.zipCode(), location.getZipCode());

        assertEquals(locationDTO.country(), response.country());
        assertEquals(locationDTO.city(), response.city());
        assertEquals(locationDTO.street(), response.street());
        assertEquals(locationDTO.zipCode(), response.zipCode());
    }
}
