package com.zeldev.zel_e_comm.unittests.locationservice;

import com.zeldev.zel_e_comm.repository.LocationRepository;
import com.zeldev.zel_e_comm.service.impl.LocationServiceImpl;
import com.zeldev.zel_e_comm.util.AuthUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class LocationServiceBaseTest {
    @Mock protected LocationRepository locationRepository;
    @Mock protected AuthUtils authUtils;

    @InjectMocks LocationServiceImpl locationService;

    static Stream<Arguments> fieldCases() {
        return Stream.of(
                Arguments.of("China", "Shanghai", "71th", "7676"),
                Arguments.of(" ", "Shanghai", null, null),
                Arguments.of(null, "Shanghai", "71th", "7676"),
                Arguments.of("", " ", null, "4242"),
                Arguments.of(null, null, null, null)
        );
    }
}
