package com.zeldev.zel_e_comm.webtests.ordercontroller;

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

public class GetOrderTest extends OrderControllerBaseTest {
    private final String PATH = "/order/get/";

    @Test
    @DisplayName(
            """
                    GIVEN: a order id path variable 
                    WHEN: it hits /order/get/{order_id}
                    THEN: returns a 200 with a OrderResponse in body
                    """
    )
    void greenPath() {
        var orderId = "398492349";
        var response = new OrderResponse(
                orderId,
                "karen@gmail",
                Set.of(new OrderItemResponse("TV", new BigDecimal("3000"), 2, new BigDecimal("20"))),
                new BigDecimal("6000"),
                OrderStatus.PENDING_PAYMENT,
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant(),
                "938923",
                PaymentMethod.BANK_TRANSFER
        );
        when(orderService.getOrderResponse(orderId)).thenReturn(response);

        var result = mockMvc.get()
                .uri(BASE_URI.concat(PATH).concat(orderId))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.OK);
        assertThat(result).bodyJson().extractingPath("$.orderId").isEqualTo(response.orderId());
        assertThat(result).bodyJson().extractingPath("$.userEmail").isEqualTo(response.userEmail());
        assertThat(result).bodyJson().extractingPath("$.userEmail").isEqualTo(response.userEmail());
        assertThat(result).bodyJson().extractingPath("$.locationPublicId").isEqualTo(response.locationPublicId());
        assertThat(result).bodyJson().extractingPath("$.status").isEqualTo(response.status().toString());
        assertThat(result).bodyJson().extractingPath("$.paymentMethod").isEqualTo(response.paymentMethod().toString());
    }
}
