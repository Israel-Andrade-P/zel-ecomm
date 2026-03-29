package com.zeldev.zel_e_comm.webtests;

import com.zeldev.zel_e_comm.controller.ProductController;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.JwtService;
import com.zeldev.zel_e_comm.service.ProductOrchestrationService;
import com.zeldev.zel_e_comm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(ProductController.class)
public class ProductControllerBaseTest {
    protected final String BASE_URI= "/api/v1";

    @MockitoBean protected ProductService productService;

    @MockitoBean protected ProductOrchestrationService orchestrationService;

    @MockitoBean protected JwtService jwtService;

    @MockitoBean protected UserRepository userRepository;

    @Autowired protected MockMvcTester mvc;
}
