package com.project.lovable.service;

import com.project.lovable.dto.subscription.UsageTodayResponse;
import com.project.lovable.dto.subscription.PlanLimitsResponse;

public interface UsageService {

    void recordTokenUsage(Long userId, int actualTokens);

    void checkDailyTokensUsage();
}
