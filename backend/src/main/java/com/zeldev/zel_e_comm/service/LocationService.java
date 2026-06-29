package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.LocationRequest;
import com.zeldev.zel_e_comm.dto.response.LocationResponse;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface LocationService {
    LocationResponse createLocation(LocationRequest location);

    @Nullable List<LocationResponse> getAll();

    @Nullable List<LocationResponse> getUserLocations();

    LocationEntity getByPublicId(String publicId);

    LocationEntity getByPublicIdAndUserEmail(String locationId, String userEmail);

    @Nullable LocationResponse updateLocation(LocationRequest locationDTO, String publicId);

    void deleteLocation(String publicId);

}
