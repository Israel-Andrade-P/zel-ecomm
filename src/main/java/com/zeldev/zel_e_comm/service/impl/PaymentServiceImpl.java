package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.PaymentRequest;
import com.zeldev.zel_e_comm.dto.response.PaymentResponse;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.PaymentEntity;
import com.zeldev.zel_e_comm.exception.AlreadyPaidException;
import com.zeldev.zel_e_comm.repository.PaymentRepository;
import com.zeldev.zel_e_comm.service.OrderService;
import com.zeldev.zel_e_comm.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zeldev.zel_e_comm.enumeration.OrderStatus.PAID;
import static com.zeldev.zel_e_comm.enumeration.OrderStatus.PENDING_PAYMENT;
import static com.zeldev.zel_e_comm.util.PaymentUtils.buildPayment;
import static com.zeldev.zel_e_comm.util.PaymentUtils.toPaymentResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Override
    public PaymentResponse pay(String orderId, PaymentRequest request) {
        OrderEntity order = orderService.getOrderEntity(orderId);
        if (order.getStatus() != PENDING_PAYMENT) {
            throw new AlreadyPaidException("Order cannot be paid in status: " + order.getStatus());
        }

        PaymentEntity payment = paymentRepository.save(buildPayment(request));
        order.setPayment(payment);
        order.setStatus(PAID);

        return toPaymentResponse(payment);
    }
}

