package com.zeldev.zel_e_comm.webtests.locationcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeldev.zel_e_comm.controller.LocationController;
import com.zeldev.zel_e_comm.dto.request.LocationDTO;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.JwtService;
import com.zeldev.zel_e_comm.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(LocationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LocationControllerBaseTest {
    protected final String BASE_URI = "/api/v1";

    @MockitoBean protected LocationService locationService;
    @MockitoBean protected UserRepository userRepository;
    @MockitoBean protected JwtService jwtService;

    @Autowired protected MockMvcTester mockMvc;

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected LocationDTO createLocationDto(String country) {
        return new LocationDTO(country, "The Shire", "Hobbit St.", "42069");
    }
}
