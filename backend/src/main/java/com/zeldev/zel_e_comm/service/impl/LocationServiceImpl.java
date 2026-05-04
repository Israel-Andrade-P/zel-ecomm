package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.request.LocationDTO;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.repository.LocationRepository;
import com.zeldev.zel_e_comm.service.LocationService;
import com.zeldev.zel_e_comm.util.AuthUtils;
import com.zeldev.zel_e_comm.util.LocationUtils;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.parameters.P;
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
    public LocationEntity getByPublicId(String publicId) {
        return findByPublicId(publicId);
    }

    @Override
    public LocationEntity getByPublicIdAndUserEmail(String publicId, String userEmail) {
        return findByPublicIdAndEmail(publicId, userEmail);
    }

    @Override
    public LocationDTO updateLocation(LocationDTO locationDTO, String publicId) {
        LocationEntity locationDB = findByPublicId(publicId);

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
    public void deleteLocation(String publicId) {
        locationRepository.delete(findByPublicId(publicId));
    }

    private LocationEntity findByPublicId(String publicId) {
        UUID uuid = UUID.fromString(publicId);
        return locationRepository.findByPublicId(uuid).orElseThrow(() -> new ResourceNotFoundException(publicId, "Location"));
    }

    private LocationEntity findByPublicIdAndEmail(String publicId, String userEmail) {
        UUID uuid = UUID.fromString(publicId);
        return locationRepository.findByPublicIdAndUserEmail(uuid, userEmail).orElseThrow(() -> new ResourceNotFoundException(publicId, "Location"));
    }
}
