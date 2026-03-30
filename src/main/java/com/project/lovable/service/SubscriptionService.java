package com.project.lovable.service;

import com.project.lovable.dto.subscription.CheckoutRequest;
import com.project.lovable.dto.subscription.CheckoutResponse;
import com.project.lovable.dto.subscription.PortalResponse;
import com.project.lovable.dto.subscription.SubscriptionResponse;
import org.jspecify.annotations.Nullable;

public interface SubscriptionService {
    SubscriptionResponse getCurrentSubscription(Long userId);

    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId);

    PortalResponse openCustomerPortal(Long userId);
}
