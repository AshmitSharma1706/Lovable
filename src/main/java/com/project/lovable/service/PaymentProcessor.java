package com.project.lovable.service;

import com.project.lovable.dto.subscription.CheckoutRequest;
import com.project.lovable.dto.subscription.CheckoutResponse;
import com.project.lovable.dto.subscription.PortalResponse;
import com.stripe.model.StripeObject;

import java.util.Map;

public interface PaymentProcessor {
    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse openCustomerPortal(Long userId);

    void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata);
}
