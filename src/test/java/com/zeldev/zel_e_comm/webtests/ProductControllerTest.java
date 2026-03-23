package com.zeldev.zel_e_comm.webtests;

import com.zeldev.zel_e_comm.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    MockMvcTester mvcTester;

    @Test
    void greenPath() {
        var response = mvcTester.get().uri("/api/v1/products").exchange();

        assertThat(response).hasStatus(HttpStatus.OK);
    }
}
