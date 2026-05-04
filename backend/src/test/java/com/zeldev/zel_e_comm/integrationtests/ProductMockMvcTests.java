//package com.zeldev.zel_e_comm.integrationtests;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
//import org.springframework.test.web.servlet.assertj.MockMvcTester;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ProductMockMvcTests {
//    @Autowired
//    MockMvcTester mockMvc;
//
//    @Test
//    void getProducts() {
//        var response = mockMvc.get().uri("/api/v1/products").exchange();
//
//        assertThat(response).hasStatus(200);
//    }
//}
