package com.project.lovable.service.impl;

import com.project.lovable.dto.subscription.PlanResponse;
import com.project.lovable.service.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }
}
