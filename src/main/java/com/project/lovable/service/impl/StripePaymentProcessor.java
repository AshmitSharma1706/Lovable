package com.project.lovable.service.impl;

import com.project.lovable.dto.subscription.CheckoutRequest;
import com.project.lovable.dto.subscription.CheckoutResponse;
import com.project.lovable.dto.subscription.PortalResponse;
import com.project.lovable.entity.Plan;
import com.project.lovable.error.ResourceNotFoundException;
import com.project.lovable.repository.PlanRepository;
import com.project.lovable.security.AuthUtil;
import com.project.lovable.service.PaymentProcessor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StripePaymentProcessor implements PaymentProcessor {

    PlanRepository planRepository;
    AuthUtil authUtil;

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {
        Long userId= authUtil.getCurrentUserId();
        Plan plan=planRepository.findById(request.planId()).orElseThrow(
                ()-> new ResourceNotFoundException("Plan", request.planId().toString())
        );
        return null;
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }
}
