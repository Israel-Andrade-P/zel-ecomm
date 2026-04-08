package com.zeldev.zel_e_comm.webtests.ordercontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zeldev.zel_e_comm.dto.request.OrderRequest;
import com.zeldev.zel_e_comm.dto.response.OrderItemResponse;
import com.zeldev.zel_e_comm.dto.response.OrderResponse;
import com.zeldev.zel_e_comm.enumeration.OrderStatus;
import com.zeldev.zel_e_comm.enumeration.PaymentMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class PlaceOrderTest extends OrderControllerBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a OrderRequest in body
                    WHEN: it hits /order/place
                    THEN: returns a 200 with a OrderResponse in body
                    """
    )
    void greenPath() throws JsonProcessingException {
        var request = new OrderRequest("locationId");
        var response = new OrderResponse(
                "orderId",
                "karen@gmail",
                Set.of(new OrderItemResponse("TV", new BigDecimal("3000"), 2, new BigDecimal("20"))),
                new BigDecimal("6000"),
                OrderStatus.PENDING_PAYMENT,
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant(),
                request.locationPublicId(),
                PaymentMethod.BANK_TRANSFER
        );
        when(orderService.createOrder(request)).thenReturn(response);

        var result = mockMvc.post()
                .uri(BASE_URI.concat("/order/place"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson().extractingPath("$.orderId").isEqualTo(response.orderId());
        assertThat(result).bodyJson().extractingPath("$.userEmail").isEqualTo(response.userEmail());
        assertThat(result).bodyJson().extractingPath("$.userEmail").isEqualTo(response.userEmail());
        assertThat(result).bodyJson().extractingPath("$.locationPublicId").isEqualTo(request.locationPublicId());
        assertThat(result).bodyJson().extractingPath("$.status").isEqualTo(response.status().toString());
        assertThat(result).bodyJson().extractingPath("$.paymentMethod").isEqualTo(response.paymentMethod().toString());
    }
}
