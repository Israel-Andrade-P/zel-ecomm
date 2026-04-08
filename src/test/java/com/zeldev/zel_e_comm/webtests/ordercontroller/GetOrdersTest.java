package com.zeldev.zel_e_comm.webtests.ordercontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetOrdersTest extends OrderControllerBaseTest{
    private final String PATH = "/admin/orders/all";

    @Test
    @DisplayName(
            """ 
                    WHEN: it hits /admin/orders/all
                    THEN: returns a 200 with a collection of OrderResponse in body
                    """
    )
    void greenPath() {
        var or1 = new OrderResponse(
                "orderId",
                "karen@gmail",
                Set.of(new OrderItemResponse("TV", new BigDecimal("3000"), 2, new BigDecimal("20"))),
                new BigDecimal("6000"),
                OrderStatus.PENDING_PAYMENT,
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant(),
                "938923",
                PaymentMethod.BANK_TRANSFER
        );
        List<OrderResponse> response = List.of(or1);

        when(orderService.getOrders()).thenReturn(response);

        var result = mockMvc.get()
                .uri(BASE_URI.concat(PATH))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.OK);
        assertThat(result).bodyJson().extractingPath("$[0].orderId").isEqualTo(or1.orderId());
        assertThat(result).bodyJson().extractingPath("$[0].userEmail").isEqualTo(or1.userEmail());
    }
}
