package com.changolaxtra.cloud.ratelimiter.storage;

import com.changolaxtra.cloud.ratelimiter.core.PlanLimitBucket;

public interface PlanLimitStorage {

  PlanLimitBucket getPlanLimit(String apiKey);

  boolean saveOrUpdate(PlanLimitBucket planLimitBucket);
}
