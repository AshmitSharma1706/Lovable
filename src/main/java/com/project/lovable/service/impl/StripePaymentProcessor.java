package com.project.lovable.service.impl;

import com.project.lovable.dto.subscription.CheckoutRequest;
import com.project.lovable.dto.subscription.CheckoutResponse;
import com.project.lovable.dto.subscription.PortalResponse;
import com.project.lovable.entity.Plan;
import com.project.lovable.entity.User;
import com.project.lovable.error.ResourceNotFoundException;
import com.project.lovable.repository.PlanRepository;
import com.project.lovable.repository.UserRepository;
import com.project.lovable.security.AuthUtil;
import com.project.lovable.service.PaymentProcessor;
import com.stripe.exception.StripeException;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StripePaymentProcessor implements PaymentProcessor {

    private final PlanRepository planRepository;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;

    @Value("${client.url}")
    private String frontendUrl;

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {
        Long userId= authUtil.getCurrentUserId();
        User user= userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", userId.toString())
        );
        Plan plan=planRepository.findById(request.planId()).orElseThrow(
                ()-> new ResourceNotFoundException("Plan", request.planId().toString())
        );

        var params= SessionCreateParams.builder()
                .addLineItem(SessionCreateParams.LineItem.builder().setPrice(plan.getStripePriceId()).build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSubscriptionData(
                        new SessionCreateParams.SubscriptionData.Builder()
                                .setBillingMode(SessionCreateParams.SubscriptionData.BillingMode.builder()
                                        .setType(SessionCreateParams.SubscriptionData.BillingMode.Type.FLEXIBLE)
                                                .build())
                                .build()
                )
                .setSuccessUrl(frontendUrl + "success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendUrl + "cancel.html")
                .putMetadata("user_id", userId.toString())
                .putMetadata("plan_id", plan.getId().toString());

        try {
            String stripeCustomerId=user.getStripeCustomerId();
            if(stripeCustomerId==null || stripeCustomerId.isEmpty()){
                params.setCustomerEmail(user.getUsername());
            }else {
                params.setCustomer(stripeCustomerId);
            }
            Session session=Session.create(params.build());
            return  new CheckoutResponse(session.getUrl());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }

    @Override
    public void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata) {

    }
}
