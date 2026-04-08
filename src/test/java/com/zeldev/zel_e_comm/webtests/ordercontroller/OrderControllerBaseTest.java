package com.zeldev.zel_e_comm.webtests.ordercontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeldev.zel_e_comm.controller.OrderController;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.JwtService;
import com.zeldev.zel_e_comm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerBaseTest {
    protected final String BASE_URI = "/api/v1";
    @MockitoBean protected OrderService orderService;
    @MockitoBean protected JwtService jwtService;
    @MockitoBean protected UserRepository userRepository;

    @Autowired protected MockMvcTester mockMvc;

    protected ObjectMapper objectMapper = new ObjectMapper();
}
