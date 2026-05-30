package com.project.lovable.service.impl;

import com.project.lovable.dto.subscription.SubscriptionResponse;
import com.project.lovable.entity.Plan;
import com.project.lovable.entity.Subscription;
import com.project.lovable.entity.User;
import com.project.lovable.enums.SubscriptionStatus;
import com.project.lovable.error.ResourceNotFoundException;
import com.project.lovable.mapper.SubscriptionMapper;
import com.project.lovable.repository.PlanRepository;
import com.project.lovable.repository.SubscriptionRepository;
import com.project.lovable.repository.UserRepository;
import com.project.lovable.security.AuthUtil;
import com.project.lovable.service.SubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionServiceImpl implements SubscriptionService {

    AuthUtil authUtil;
    SubscriptionRepository subscriptionRepository;
    SubscriptionMapper subscriptionMapper;
    UserRepository userRepository;
    PlanRepository planRepository;

    @Override
    public SubscriptionResponse getCurrentSubscription() {
        Long userId= authUtil.getCurrentUserId();

        var currentSubscription=subscriptionRepository.findByUserIdAndStatusIn(userId, Set.of
                (SubscriptionStatus.ACTIVE,SubscriptionStatus.PAST_DUE, SubscriptionStatus.TRIALING)
        ).orElse(
                new Subscription()
        );

        return subscriptionMapper.toSubscriptionResponse(currentSubscription);
    }

    @Override
    public void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId) {
        boolean exists= subscriptionRepository.existsByStripeSubscriptionId(subscriptionId);
        if(exists)return;

        User user=getUser(userId);
        Plan plan=getPlan(planId);

        Subscription subscription=Subscription.builder()
                .user(user)
                .plan(plan)
                .stripeSubscriptionId(subscriptionId)
                .status(SubscriptionStatus.INCOMPLETE)
                .build();

        subscriptionRepository.save(subscription);
    }

    @Override
    public void updateSubscription(String id, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId) {

    }

    @Override
    public void cancelSubscription(String id) {

    }

    @Override
    public void renewSubscriptionPeriod(String gatewaySubscriptionId, Instant periodStart, Instant periodEnd) {
        Subscription subscription= getSubscription(gatewaySubscriptionId);
        Instant newStart= periodStart!=null ? periodStart:subscription.getCurrentPeriodEnd();
        subscription.setCurrentPeriodStart(newStart);
        subscription.setCurrentPeriodEnd(periodEnd);

        if (subscription.getStatus()==SubscriptionStatus.PAST_DUE || subscription.getStatus()==SubscriptionStatus.INCOMPLETE){
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }
        subscriptionRepository.save(subscription);
    }

    @Override
    public void markSubscriptionPastDue(String gatewaySubscriptionId) {

    }

    private User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("User", userId.toString()));
    }

    private Plan getPlan(Long planId){
        return planRepository.findById(planId)
                .orElseThrow( () -> new ResourceNotFoundException("User", planId.toString()));
    }

    private Subscription getSubscription(String gatewaySubscriptionId) {
        return subscriptionRepository.findByStripeSubscriptionId(gatewaySubscriptionId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Subscription", gatewaySubscriptionId)
                );
    }
}
