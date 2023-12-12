package com.changolaxtra.cloud.ratelimiter.core;

import com.changolaxtra.cloud.ratelimiter.core.PlanLimitBucket;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class PlanLimitDataRoot {
    private final Set<PlanLimitBucket> planLimitBuckets;

    public PlanLimitDataRoot() {
        planLimitBuckets = new HashSet<>();
    }

    public boolean addPlanLimitBucket(final PlanLimitBucket planLimitBucket) {
        return planLimitBuckets.add(planLimitBucket);
    }
}
