package com.zeldev.zel_e_comm.service;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerSearchParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.zeldev.zel_e_comm.common.StripeWebhookData;
import com.zeldev.zel_e_comm.dto.response.StripeConfirmationResponse;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.enumeration.PaymentType;
import com.zeldev.zel_e_comm.exception.PaymentProviderException;
import com.zeldev.zel_e_comm.exception.UnsupportedPaymentMethodException;
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

    public PaymentIntent paymentIntent(OrderEntity order) {
        var user = order.getUser();

        var amountInCents = order.getTotalPrice().multiply(BigDecimal.valueOf(100)).longValueExact();

        try {
            //IMPLEMENTING CUSTOMER STRIPE
            CustomerSearchParams customerParams = CustomerSearchParams.builder()
                    .setQuery("email:'" + user.getEmail() + "'")
                    .build();
            CustomerSearchResult result = Customer.search(customerParams);

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
            log.info("Retrieving PaymentIntent with id {}", paymentIntentId);
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            boolean success = "succeeded".equals(paymentIntent.getStatus());
            log.info("PaymentIntent retrieved with status {}", paymentIntent.getStatus());
            String orderId = paymentIntent.getMetadata().get("orderId");
            log.info("StripeConfirmationResponse being built...");
            return StripeConfirmationResponse.builder()
                    .success(success)
                    .orderId(orderId)
                    .build();
        } catch (StripeException exp) {
            throw new PaymentProviderException(String.format("PaymentIntent with %s not found", paymentIntentId), exp);
        }
    }

    public StripeWebhookData handleWebhook(String payload, String signature) {
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

                String stripePaymentMethodId = paymentIntent.getPaymentMethod();
                PaymentMethod stripePaymentMethod = PaymentMethod.retrieve(stripePaymentMethodId);
                PaymentType paymentType = mapPaymentMethod(stripePaymentMethod);

                log.info("PaymentIntent {} succeeded for order {}", paymentIntentId, orderId);

                return StripeWebhookData.builder()
                        .orderId(orderId)
                        .paymentIntentId(paymentIntentId)
                        .status(paymentIntent.getStatus())
                        .paymentType(paymentType)
                        .build();
            }

        } catch (SignatureVerificationException e) {
            log.error("Invalid Stripe webhook signature", e);
            throw new PaymentProviderException("Invalid Stripe webhook signature", e);
        } catch (StripeException e) {
            log.error("StripeException thrown", e);
            throw new PaymentProviderException("StripeException thrown", e);
        }
        return null;
    }

    private PaymentType mapPaymentMethod(PaymentMethod stripePaymentMethod) {
        return switch (stripePaymentMethod.getType()) {
            case "card" -> PaymentType.CREDIT_CARD;
            case "pix" -> PaymentType.PIX;
            case "boleto" -> PaymentType.BOLETO;
            default -> throw new UnsupportedPaymentMethodException("This payment method is not supported");
        };
    }

    @PostConstruct
    private void init() {
        Stripe.apiKey = stripeApiKey;
    }
}
