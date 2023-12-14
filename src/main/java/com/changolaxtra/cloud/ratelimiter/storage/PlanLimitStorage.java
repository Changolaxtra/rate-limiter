package com.changolaxtra.cloud.ratelimiter.storage;

import com.changolaxtra.cloud.ratelimiter.core.PlanLimitBucket;

/**
 * Contract for to read and write {@link PlanLimitBucket} in the storage.
 */
public interface PlanLimitStorage {

  /**
   * Retrieves the {@link PlanLimitBucket} using the API Key.
   */
  PlanLimitBucket getPlanLimit(String apiKey);

  /**
   * Save or Update the {@link PlanLimitBucket} in the storage.
   */
  boolean saveOrUpdate(PlanLimitBucket planLimitBucket);
}
