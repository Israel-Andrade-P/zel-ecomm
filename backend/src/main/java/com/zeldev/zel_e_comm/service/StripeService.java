package com.zeldev.zel_e_comm.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.zeldev.zel_e_comm.dto.request.StripePaymentRequest;
import com.zeldev.zel_e_comm.exception.PaymentProviderException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeService {
    @Value("${stripe.secret.key}")
    private String stripeApiKey;

    public PaymentIntent paymentIntent(StripePaymentRequest request) {
        try {
            log.info("Creating PaymentIntent...");
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(request.amount())
                    .setCurrency(request.currency())
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();
            log.info("PaymentIntent created, client secret generated");
            return PaymentIntent.create(params);
        } catch (StripeException exp) {
            throw new PaymentProviderException("Failed to create Stripe payment intent", exp);
        }
    }

    @PostConstruct
    private void init() {
        Stripe.apiKey = stripeApiKey;
    }
}
