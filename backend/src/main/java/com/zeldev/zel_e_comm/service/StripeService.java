package com.zeldev.zel_e_comm.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.zeldev.zel_e_comm.dto.request.StripePaymentRequest;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.exception.PaymentProviderException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeService {
    @Value("${stripe.secret.key}")
    private String stripeApiKey;

    public PaymentIntent paymentIntent(OrderEntity order) {
        var amountInCents = order.getTotalPrice().multiply(BigDecimal.valueOf(100)).longValueExact();

        try {
            log.info("Creating PaymentIntent for order {}", order.getPublicId());
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency("usd")
                    .putMetadata("orderId", order.getPublicId())
                    .putMetadata("userEmail", order.getUser().getEmail())
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();
            var paymentIntent = PaymentIntent.create(params);
            log.info("PaymentIntent {} created for order {}", paymentIntent.getId(), order.getPublicId());
            return paymentIntent;
        } catch (StripeException exp) {
            throw new PaymentProviderException("Failed to create Stripe payment intent", exp);
        }
    }

    @PostConstruct
    private void init() {
        Stripe.apiKey = stripeApiKey;
    }
}
