package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.LocationDTO;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface LocationService {
    LocationDTO createLocation(LocationDTO location);

    @Nullable List<LocationDTO> getAll();

    @Nullable List<LocationDTO> getUserLocations();

    @Nullable LocationDTO updateLocation(LocationDTO locationDTO, String zipCode);

    void deleteLocation(String zipCode);
}
