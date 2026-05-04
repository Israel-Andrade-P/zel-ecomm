package com.zeldev.zel_e_comm.unittests.paymentservice;

import com.zeldev.zel_e_comm.dto.request.PaymentRequest;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.PaymentEntity;
import com.zeldev.zel_e_comm.exception.AlreadyPaidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.zeldev.zel_e_comm.enumeration.OrderStatus.PAID;
import static com.zeldev.zel_e_comm.enumeration.OrderStatus.PENDING_PAYMENT;
import static com.zeldev.zel_e_comm.enumeration.PaymentMethod.BANK_TRANSFER;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PayTest extends PaymentServiceBaseTest{

    @Test
    @DisplayName(
            """
                   GIVEN: a payment request and a order id
                   WHEN: pay method is called
                   THEN: a new payment entity is created
                   AND: returns payment response 
                   """
    )
    void greenPath() {
        String orderId = "orderId";
        PaymentRequest paymentRequest = new PaymentRequest(BANK_TRANSFER);
        OrderEntity order = new OrderEntity();
        order.setStatus(PENDING_PAYMENT);

        when(orderService.getOrderEntity(orderId)).thenReturn(order);
        when(paymentRepository.save(any(PaymentEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = paymentService.pay(orderId, paymentRequest);

        ArgumentCaptor<PaymentEntity> captor = ArgumentCaptor.forClass(PaymentEntity.class);

        verify(paymentRepository).save(captor.capture());
        verify(orderService).getOrderEntity(orderId);

        PaymentEntity payment = captor.getValue();

        assertEquals(paymentRequest.paymentMethod(), payment.getPaymentMethod());
        assertNotNull(payment.getPgId());
        assertNotNull(payment.getPgName());
        assertNotNull(payment.getPgStatus());
        assertNotNull(payment.getPgResponseMessage());

        assertEquals(payment, order.getPayment());
        assertEquals(PAID, order.getStatus());

        assertEquals(payment.getPaymentMethod(), response.paymentMethod());
        assertEquals(payment.getPgId(), response.pgId());
        assertEquals(payment.getPgStatus(), response.pgStatus());
        assertEquals(payment.getPgResponseMessage(), response.pgResponseMessage());
    }

    @Test
    @DisplayName(
            """
                   GIVEN: a payment request and a order id
                   WHEN: pay method is called
                   THEN: order status isn't PENDING_PAYMENT
                   AND: throws exception
                   """
    )
    void redPath() {
        String orderId = "orderId";
        PaymentRequest paymentRequest = new PaymentRequest(BANK_TRANSFER);
        OrderEntity order = new OrderEntity();
        order.setStatus(PAID);

        when(orderService.getOrderEntity(orderId)).thenReturn(order);

        assertThrows(AlreadyPaidException.class, () -> paymentService.pay(orderId, paymentRequest));

        verify(paymentRepository, never()).save(any());
    }
}
