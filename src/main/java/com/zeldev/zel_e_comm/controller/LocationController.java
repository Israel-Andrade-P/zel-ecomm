package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.domain.Response;
import com.zeldev.zel_e_comm.dto.dto_class.LocationDTO;
import com.zeldev.zel_e_comm.service.LocationService;
import com.zeldev.zel_e_comm.util.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping("/add")
    public ResponseEntity<LocationDTO> create(@RequestBody @Valid LocationDTO locationDTO) {
        return ResponseEntity.status(CREATED).body(locationService.createLocation(locationDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LocationDTO>> findAll() {
        return ResponseEntity.status(OK).body(locationService.getAll());
    }

    @GetMapping("/user")
    public ResponseEntity<List<LocationDTO>> getUserLocations() {
        return ResponseEntity.status(OK).body(locationService.getUserLocations());
    }

    @PutMapping("/update/{zip_code}")
    public ResponseEntity<LocationDTO> update(@RequestBody LocationDTO locationDTO,
                                              @PathVariable("zip_code") String zipCode) {
        return ResponseEntity.status(OK).body(locationService.updateLocation(locationDTO, zipCode));
    }

    @DeleteMapping("/delete/{zip_code}")
    public ResponseEntity<Response> delete(@PathVariable("zip_code") String zipCode, HttpServletRequest request) {
        locationService.deleteLocation(zipCode);
        return ResponseEntity.status(OK).body(RequestUtils.getResponse(request, Collections.emptyMap(), "Location deleted", OK));
    }
}
