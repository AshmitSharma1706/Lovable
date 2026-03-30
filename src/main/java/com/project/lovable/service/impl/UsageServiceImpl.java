package com.project.lovable.service.impl;

import com.project.lovable.dto.subscription.PlanLimitsResponse;
import com.project.lovable.dto.subscription.UsageTodayResponse;
import com.project.lovable.service.UsageService;
import org.springframework.stereotype.Service;

@Service
public class UsageServiceImpl implements UsageService {
    @Override
    public UsageTodayResponse getTodayUsageOfUser(Long userId) {
        return null;
    }

    @Override
    public PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId) {
        return null;
    }
}
