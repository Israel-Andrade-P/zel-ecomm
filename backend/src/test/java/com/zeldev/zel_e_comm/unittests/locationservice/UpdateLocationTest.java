package com.zeldev.zel_e_comm.unittests.locationservice;

import com.zeldev.zel_e_comm.dto.request.LocationDTO;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateLocationTest extends LocationServiceBaseTest{

    @ParameterizedTest
    @MethodSource("fieldCases")
    @DisplayName(
            """
                    GIVEN: a location DTO and a string publicId
                    WHEN: updateLocation is called
                    THEN: updates requested location
                    """
    )
    void greenPath(String country, String city, String street, String zipCode) {
        String publicId = UUID.randomUUID().toString();
        LocationDTO locationDTO = new LocationDTO(country, city, street, zipCode);

        LocationEntity location = new LocationEntity();
        location.setCountry("Australia");
        location.setCity("Melbourne");
        location.setStreet("71th");
        location.setZipCode("456");

        when(locationRepository.findByPublicId(UUID.fromString(publicId))).thenReturn(Optional.of(location));

        var response = locationService.updateLocation(locationDTO, publicId);

        if (country != null && !country.isBlank()) assertEquals(locationDTO.country(), response.country());
        else assertEquals(location.getCountry(), response.country());

        if (city != null && !city.isBlank()) assertEquals(locationDTO.city(), response.city());
        else assertEquals(location.getCity(), response.city());

        if (street != null && !street.isBlank()) assertEquals(locationDTO.street(), response.street());
        else assertEquals(location.getStreet(), response.street());

        if (zipCode != null && !zipCode.isBlank()) assertEquals(locationDTO.zipCode(), response.zipCode());
        else assertEquals(location.getZipCode(), response.zipCode());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a location DTO and a string publicId
                    WHEN: updateLocation is called
                    THEN: location doesn't exist
                    AND: throws exception
                    """
    )
    void redPath() {
        String publicId = UUID.randomUUID().toString();
        LocationDTO locationDTO = new LocationDTO("China", "city", "street", "zipCode");

        when(locationRepository.findByPublicId(UUID.fromString(publicId))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> locationService.updateLocation(locationDTO, publicId));
    }
}
