package com.zeldev.zel_e_comm.service;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.zeldev.zel_e_comm.common.StripeWebhookData;
import com.zeldev.zel_e_comm.dto.response.StripeConfirmationResponse;
import com.zeldev.zel_e_comm.entity.LocationEntity;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.PaymentType;
import com.zeldev.zel_e_comm.exception.PaymentProviderException;
import com.zeldev.zel_e_comm.exception.UnsupportedPaymentMethodException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

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

        try {
            var customer = getOrCreateStripeCustomer(user, order.getLocation());

            log.info("Creating PaymentIntent for order {}", order.getPublicId());
            var paymentIntent = createPaymentIntent(order, customer);
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

    public Optional<StripeWebhookData> handleWebhook(String payload, String signature) {
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

                return Optional.of(
                        StripeWebhookData.builder()
                                .orderId(orderId)
                                .paymentIntentId(paymentIntentId)
                                .status(paymentIntent.getStatus())
                                .paymentType(paymentType)
                                .build()
                );
            }

        } catch (SignatureVerificationException e) {
            log.error("Invalid Stripe webhook signature", e);
            throw new PaymentProviderException("Invalid Stripe webhook signature", e);
        } catch (StripeException e) {
            log.error("StripeException thrown", e);
            throw new PaymentProviderException("StripeException thrown", e);
        }
        return Optional.empty();
    }

    private PaymentIntent createPaymentIntent(OrderEntity order, Customer customer) throws StripeException {
        final String description = String.format("Order created for %s", order.getUser().getEmail());
        var amountInCents = order.getTotalPrice().multiply(BigDecimal.valueOf(100)).longValueExact();

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("usd")
                .setCustomer(customer.getId())
                .setDescription(description)
                .putMetadata("orderId", order.getPublicId())
                .putMetadata("userEmail", order.getUser().getEmail())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .build();
        return PaymentIntent.create(params);
    }

    private Customer getOrCreateStripeCustomer(UserEntity user, LocationEntity location) throws StripeException {
        var userEmail = user.getEmail();
        var result = searchCustomer(userEmail);
        Customer customer;
        if (result.getData().isEmpty()) customer = createCustomer(user, location);
        else customer = result.getData().getFirst();

        return customer;
    }

    private CustomerSearchResult searchCustomer(String userEmail) throws StripeException {
        CustomerSearchParams customerParams = CustomerSearchParams.builder()
                .setQuery(String.format("email:'%s'", userEmail))
                .build();
        return Customer.search(customerParams);
    }

    private Customer createCustomer(UserEntity user, LocationEntity location) throws StripeException {
        CustomerCreateParams createParams = CustomerCreateParams.builder()
                .setName(user.getUsername())
                .setEmail(user.getEmail())
                .setAddress(
                        CustomerCreateParams.Address.builder()
                                .setLine1(location.getStreet())
                                .setCity(location.getCity())
                                .setPostalCode(location.getZipCode())
                                .setCountry(location.getCountry())
                                .build()
                )
                .build();
        return Customer.create(createParams);
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
