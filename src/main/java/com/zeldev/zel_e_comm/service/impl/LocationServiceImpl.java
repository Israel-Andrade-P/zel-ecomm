package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.LocationDTO;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.repository.LocationRepository;
import com.zeldev.zel_e_comm.service.LocationService;
import com.zeldev.zel_e_comm.util.AuthUtils;
import com.zeldev.zel_e_comm.util.LocationUtils;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.zeldev.zel_e_comm.util.LocationUtils.buildLocation;
import static com.zeldev.zel_e_comm.util.LocationUtils.toDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final AuthUtils authUtils;

    @Override
    public LocationDTO createLocation(LocationDTO location) {
        return toDTO(locationRepository.save(buildLocation(location, authUtils.getLoggedInUser())));
    }

    @Override
    @Transactional(readOnly = true)
    public @Nullable List<LocationDTO> getAll() {
        return locationRepository.findAll().stream().map(LocationUtils::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public @Nullable List<LocationDTO> getUserLocations() {
        return locationRepository.findLocationsByUserEmail(authUtils.getLoggedInEmail()).stream().map(LocationUtils::toDTO).toList();
    }

    @Override
    public @Nullable LocationEntity getByPublicId(UUID publicId) {
        return locationRepository.findByPublicId(publicId);
    }

    @Override
    public @Nullable LocationDTO updateLocation(LocationDTO locationDTO, String zipCode) {
        LocationEntity locationDB = getLocationByZipCodeAndUserEmail(zipCode);

        if (locationDTO.city() != null && !locationDTO.city().isBlank()) {
            locationDB.setCity(locationDTO.city());
        }

        if (locationDTO.country() != null && !locationDTO.country().isBlank()) {
            locationDB.setCountry(locationDTO.country());
        }

        if (locationDTO.street() != null && !locationDTO.street().isBlank()) {
            locationDB.setStreet(locationDTO.street());
        }

        if (locationDTO.zipCode() != null && !locationDTO.zipCode().isBlank()) {
            locationDB.setZipCode(locationDTO.zipCode());
        }

        return toDTO(locationDB);
    }

    @Override
    public void deleteLocation(String zipCode) {
        locationRepository.delete(getLocationByZipCodeAndUserEmail(zipCode));
    }

    private LocationEntity getLocationByZipCodeAndUserEmail(String zipCode) {
        return locationRepository.findLocationByZipCodeAndUserEmail(zipCode, authUtils.getLoggedInEmail())
                .orElseThrow(() -> new ResourceNotFoundException(zipCode, "Location"));
    }
}
