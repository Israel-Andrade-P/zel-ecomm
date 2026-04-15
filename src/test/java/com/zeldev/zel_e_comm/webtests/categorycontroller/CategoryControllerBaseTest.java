package com.zeldev.zel_e_comm.webtests.categorycontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeldev.zel_e_comm.controller.CategoryController;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import com.zeldev.zel_e_comm.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerBaseTest {
    protected final String BASE_URI = "/api/v1";

    @MockitoBean protected CategoryService categoryService;
    @MockitoBean protected JwtService jwtService;
    @MockitoBean protected UserRepository userRepository;

    @Autowired protected MockMvcTester mockMvc;

    protected ObjectMapper objectMapper = new ObjectMapper();
}
