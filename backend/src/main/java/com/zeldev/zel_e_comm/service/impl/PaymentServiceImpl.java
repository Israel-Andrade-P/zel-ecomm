package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.request.StripePaymentRequest;
import com.zeldev.zel_e_comm.dto.response.StripeConfirmationResponse;
import com.zeldev.zel_e_comm.dto.response.StripePaymentResponse;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.PaymentEntity;
import com.zeldev.zel_e_comm.enumeration.PaymentType;
import com.zeldev.zel_e_comm.exception.AlreadyPaidException;
import com.zeldev.zel_e_comm.repository.PaymentRepository;
import com.zeldev.zel_e_comm.service.OrderService;
import com.zeldev.zel_e_comm.service.PaymentService;
import com.zeldev.zel_e_comm.service.StripeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zeldev.zel_e_comm.enumeration.OrderStatus.PENDING_PAYMENT;
import static com.zeldev.zel_e_comm.util.PaymentUtils.buildPayment;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final StripeService stripeService;

//    @Override
//    public PaymentResponse pay(String orderId, PaymentRequest request) {
//        OrderEntity order = orderService.getOrderEntity(orderId);
//        if (order.getStatus() != PENDING_PAYMENT) {
//            throw new AlreadyPaidException("Order cannot be paid in status: " + order.getStatus());
//        }
//
//        PaymentEntity payment = paymentRepository.save(buildPayment(request));
//        order.setPayment(payment);
//        order.setStatus(PAID);
//
//        return toPaymentResponse(payment);
//    }

    @Override
    public PaymentEntity persistPaymentEntity(PaymentType paymentType, String pgId, String pgStatus) {
        return paymentRepository.save(buildPayment(paymentType, pgId, pgStatus));
    }

    //grab orderId from StripePaymentRequest, don't trust the amount coming from frontend, load order then calculate amount that should be charged
    //follow this flow: Create Order -> PENDING_PAYMENT -> Create PaymentIntent -> Stripe Checkout/Form -> Stripe Webhook -> Create PaymentEntity -> Deduct inventory -> Clear cart -> PAID
    public StripePaymentResponse createPaymentIntent(StripePaymentRequest request) {
        OrderEntity order = orderService.getOrderEntity(request.orderId());
        if (order.getStatus() != PENDING_PAYMENT) {
            throw new AlreadyPaidException("Order cannot be paid in status: " + order.getStatus());
        }

        var paymentIntent = stripeService.paymentIntent(order);
        return StripePaymentResponse.builder()
                .clientSecret(paymentIntent.getClientSecret())
                .build();
    }

    @Override
    public StripeConfirmationResponse confirmPayment(String paymentIntentId) {
        log.info("Calling stripe service from payment service");
        return stripeService.checkPaymentIntent(paymentIntentId);
    }

    @Override
    public void handleWebhook(String payload, String signature) {

        var result = stripeService.handleWebhook(payload, signature);

        if (result.isEmpty()) return;

        var data = result.get();

        var payment = persistPaymentEntity(data.paymentType(), data.paymentIntentId(), data.status());

        orderService.markAsPaid(data.orderId(), payment);
    }
}

