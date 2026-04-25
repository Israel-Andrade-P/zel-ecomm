package com.zeldev.zel_e_comm.webtests.paymentcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeldev.zel_e_comm.controller.PaymentController;
import com.zeldev.zel_e_comm.dto.request.PaymentRequest;
import com.zeldev.zel_e_comm.dto.response.PaymentResponse;
import com.zeldev.zel_e_comm.enumeration.PaymentMethod;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.JwtService;
import com.zeldev.zel_e_comm.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PayTest {
    @MockitoBean private PaymentService paymentService;
    @MockitoBean private UserRepository userRepository;
    @MockitoBean private JwtService jwtService;

    @Autowired private MockMvcTester mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName(
            """
                    GIVEN: a order id and a PaymentRequest
                    WHEN: it hits /orders/{orderId}
                    THEN: return 200 and a PaymentResponse in body
                    """
    )
    void greenPath() throws JsonProcessingException {
        String orderId = "orderId";
        PaymentRequest request = new PaymentRequest(PaymentMethod.BANK_TRANSFER);
        PaymentResponse response = new PaymentResponse(
                PaymentMethod.BANK_TRANSFER,
                "Id",
                "status",
                "message",
                "name"
        );

        when(paymentService.pay(orderId, request)).thenReturn(response);

        var result = mockMvc
                .post()
                .uri("/api/v1/payments/orders/" + orderId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
                .exchange();

        assertThat(result).hasStatus(OK);
        assertThat(result).bodyJson().extractingPath("$.pgResponseMessage").isEqualTo(response.pgResponseMessage());
        assertThat(result).bodyJson().extractingPath("$.pgId").isEqualTo(response.pgId());
        assertThat(result).bodyJson().extractingPath("$.pgStatus").isEqualTo(response.pgStatus());
        assertThat(result).bodyJson().extractingPath("$.pgName").isEqualTo(response.pgName());
        assertThat(result).bodyJson().extractingPath("$.paymentMethod").isEqualTo(response.paymentMethod().toString());
        verify(paymentService).pay(orderId, request);
    }
}
