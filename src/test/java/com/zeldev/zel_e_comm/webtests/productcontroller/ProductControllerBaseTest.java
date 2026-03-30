package com.zeldev.zel_e_comm.webtests.productcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeldev.zel_e_comm.controller.ProductController;
import com.zeldev.zel_e_comm.dto.request.ProductDTO;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.JwtService;
import com.zeldev.zel_e_comm.service.ProductOrchestrationService;
import com.zeldev.zel_e_comm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.math.BigDecimal;
import java.util.UUID;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerBaseTest {
    protected final String BASE_URI= "/api/v1";
    protected final String PRODUCT_ID = UUID.randomUUID().toString();

    @MockitoBean protected ProductService productService;

    @MockitoBean protected ProductOrchestrationService orchestrationService;

    @MockitoBean protected JwtService jwtService;

    @MockitoBean protected UserRepository userRepository;

    @Autowired protected MockMvcTester mvc;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected ProductDTO createDto(String name, Integer quantity) {
        return new ProductDTO(
                "1",
                name,
                "Nice pan",
                "pan.png",
                quantity,
                new BigDecimal("100.00"),
                0,
                new BigDecimal("90.00")
        );
    }
}
