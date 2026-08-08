package com.zeldev.zel_e_comm.service;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.zeldev.zel_e_comm.dto.response.StripeConfirmationResponse;
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
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final OrderService orderService;

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

    public StripeConfirmationResponse checkPaymentIntent(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            boolean success = "succeeded".equals(paymentIntent.getStatus());
            String orderId = paymentIntent.getMetadata().get("orderId");
            return StripeConfirmationResponse.builder()
                    .success(success)
                    .orderId(orderId)
                    .build();
        } catch (StripeException exp) {
            throw new PaymentProviderException(String.format("PaymentIntent with %s not found", paymentIntentId), exp);
        }
    }

    //Webhook not being triggered, also frontend stripePaymentConfirmation action going in the catch block
    public void handleWebhook(String payload, String signature) {
        try {
            Event event = Webhook.constructEvent(payload, signature, webhookSecret);
            log.info("Received Stripe event: {} ({})", event.getType(), event.getId());

            if ("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent paymentIntent = event.getDataObjectDeserializer()
                        .getObject()
                        .map(PaymentIntent.class::cast)
                        .orElseThrow(() -> new PaymentProviderException("Failed to deserialize Payment Intent"));
                String paymentIntentId = paymentIntent.getId();
                String orderId = paymentIntent.getMetadata().get("orderId");
                log.info("PaymentIntent {} succeeded for order {}", paymentIntentId, orderId);

                orderService.markAsPaid(orderId);
            }

        } catch (SignatureVerificationException e) {
            log.error("Invalid Stripe webhook signature", e);
            throw new PaymentProviderException("Invalid Stripe webhook signature", e);
        }
    }

    @PostConstruct
    private void init() {
        Stripe.apiKey = stripeApiKey;
    }
}
