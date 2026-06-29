package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.domain.Response;
import com.zeldev.zel_e_comm.dto.request.LocationRequest;
import com.zeldev.zel_e_comm.dto.response.LocationResponse;
import com.zeldev.zel_e_comm.service.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.zeldev.zel_e_comm.util.RequestUtils.getResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Location APIs", description = "APIs that manage locations")
public class LocationController {
    private final LocationService locationService;

    @PostMapping("/locations/add")
    public ResponseEntity<LocationResponse> create(@RequestBody @Valid LocationRequest locationDTO) {
        return ResponseEntity.status(CREATED).body(locationService.createLocation(locationDTO));
    }

    @GetMapping("/admin/locations/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LocationResponse>> findAll() {
        return ResponseEntity.status(OK).body(locationService.getAll());
    }

    @GetMapping("/locations/user")
    public ResponseEntity<List<LocationResponse>> getUserLocations() {
        return ResponseEntity.status(OK).body(locationService.getUserLocations());
    }

    @PutMapping("/locations/update/{id}")
    @PreAuthorize("hasRole('ADMIN') || @locationSecurity.isOwner(#publicId)")
    public ResponseEntity<LocationResponse> update(@RequestBody LocationRequest locationDTO,
                                                  @PathVariable("id") String publicId) {
        return ResponseEntity.status(OK).body(locationService.updateLocation(locationDTO, publicId));
    }

    @DeleteMapping("/locations/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') || @locationSecurity.isOwner(#publicId)")
    public ResponseEntity<Response> delete(@PathVariable("id") String publicId, HttpServletRequest request) {
        locationService.deleteLocation(publicId);
        return ResponseEntity.status(OK).body(getResponse(request, Collections.emptyMap(), "Location deleted", OK));
    }
}
