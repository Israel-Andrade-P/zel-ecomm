package com.zeldev.zel_e_comm.security;

import com.zeldev.zel_e_comm.repository.LocationRepository;
import com.zeldev.zel_e_comm.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LocationSecurity {
    private final LocationRepository locationRepository;
    private final AuthUtils authUtils;

    public boolean isOwner(String publicId) {
        if (publicId == null) return false;
        UUID uuid = UUID.fromString(publicId);
        return locationRepository.existsByIdAndUserEmail(uuid, authUtils.getLoggedInEmail());
    }
}
