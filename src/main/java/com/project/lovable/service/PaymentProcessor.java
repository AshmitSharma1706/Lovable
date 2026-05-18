package com.project.lovable.service;

import com.project.lovable.dto.subscription.CheckoutRequest;
import com.project.lovable.dto.subscription.CheckoutResponse;
import com.project.lovable.dto.subscription.PortalResponse;

public interface PaymentProcessor {
    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse openCustomerPortal(Long userId);
}
