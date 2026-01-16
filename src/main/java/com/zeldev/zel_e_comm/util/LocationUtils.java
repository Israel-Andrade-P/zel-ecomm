package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.dto_class.LocationDTO;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;

public class LocationUtils {
    public static LocationEntity buildLocation(LocationDTO location, UserEntity user){
        return LocationEntity.builder()
                .country(location.country())
                .city(location.city())
                .street(location.street())
                .zipCode(location.zipCode())
                .user(user)
                .build();
    }
}
