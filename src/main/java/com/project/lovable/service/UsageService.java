package com.project.lovable.service;

import com.project.lovable.dto.subscription.UsageTodayResponse;
import com.project.lovable.dto.subscription.PlanLimitsResponse;

public interface UsageService {
     UsageTodayResponse getTodayUsageOfUser(Long userId);

    PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId);
}
