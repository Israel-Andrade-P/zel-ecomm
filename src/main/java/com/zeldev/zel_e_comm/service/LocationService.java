package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.LocationDTO;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface LocationService {
    LocationDTO createLocation(LocationDTO location);

    @Nullable List<LocationDTO> getAll();

    @Nullable List<LocationDTO> getUserLocations();

    @Nullable LocationEntity getByPublicId(UUID publicId);

    @Nullable LocationDTO updateLocation(LocationDTO locationDTO, String zipCode);

    void deleteLocation(String zipCode);
}
