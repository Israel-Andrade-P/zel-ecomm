package com.zeldev.zel_e_comm.unittests.orderservice;

import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.OrderStatus;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class GetOrderResponseTest extends OrderServiceBaseTest{
    private final String ORDER_ID = "67574";

    @Test
    @DisplayName(
            """
                    GIVEN: a order id 
                    WHEN: getOrderResponse is called
                    THEN: returns order response
                    """
    )
    void greenPath() {
        UserEntity user = new UserEntity();
        user.setEmail("mike@gmail");
        LocationEntity location = new LocationEntity();
        location.setPublicId(UUID.randomUUID());
        OrderEntity order = new OrderEntity();
        order.setStatus(OrderStatus.PAID);
        order.setPublicId(ORDER_ID);
        order.setUser(user);
        order.setLocation(location);
        order.setCreatedAt(LocalDateTime.now());

        when(orderRepository.findByPublicId(order.getPublicId())).thenReturn(Optional.of(order));

        var response = orderService.getOrderResponse(ORDER_ID);

        assertEquals(ORDER_ID, response.orderId());
        assertEquals(order.getStatus(), response.status());
        assertEquals(user.getEmail(), response.userEmail());
        assertEquals(location.getPublicId().toString(), response.locationPublicId());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a order id 
                    WHEN: getOrderResponse is called
                    THEN: returns empty optional
                    AND: throws exception
                    """
    )
    void redPath() {
        when(orderRepository.findByPublicId(ORDER_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderResponse(ORDER_ID));
    }
}
