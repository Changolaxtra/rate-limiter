package com.changolaxtra.cloud.ratelimiter.core;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

/**
 * Root Storage model for {@link PlanLimitBucket}.
 */
@Getter
public class PlanLimitDataRoot {

  private final Set<PlanLimitBucket> planLimitBuckets;

  public PlanLimitDataRoot() {
    planLimitBuckets = new HashSet<>();
  }

  /**
   * Adds a {@link PlanLimitBucket} to the root collection, if the {@link PlanLimitBucket} already
   * exists in the collection it will be replaced.
   */
  public boolean addPlanLimitBucket(final PlanLimitBucket planLimitBucket) {
    return planLimitBuckets.add(planLimitBucket);
  }
}
