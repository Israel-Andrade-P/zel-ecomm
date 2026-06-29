package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.request.LocationRequest;
import com.zeldev.zel_e_comm.dto.response.LocationResponse;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;

public class LocationUtils {
    public static LocationEntity buildLocation(LocationRequest location, UserEntity user){
        return LocationEntity.builder()
                .country(location.country())
                .city(location.city())
                .street(location.street())
                .zipCode(location.zipCode())
                .user(user)
                .build();
    }

    public static LocationResponse toDTO(LocationEntity entity) {
        return LocationResponse.builder()
                .publicId(entity.getPublicId().toString())
                .city(entity.getCity())
                .country(entity.getCountry())
                .street(entity.getStreet())
                .zipCode(entity.getZipCode())
                .build();
    }
}
