package com.project.lovable.mapper;

import com.project.lovable.dto.subscription.PlanResponse;
import com.project.lovable.dto.subscription.SubscriptionResponse;
import com.project.lovable.entity.Plan;
import com.project.lovable.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface SubscriptionMapper {

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    PlanResponse toPlanResponse(Plan plan);
}
