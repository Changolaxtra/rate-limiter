package com.changolaxtra.cloud.ratelimiter.storage;

import com.changolaxtra.cloud.ratelimiter.core.PlanLimitBucket;
import com.changolaxtra.cloud.ratelimiter.core.PlanLimitDataRoot;
import java.util.HashSet;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link PlanLimitStorage} using {@link EmbeddedStorageManager} as main storage.
 */
@Repository
public class EmbeddedPlanLimitStorage implements PlanLimitStorage {

  private final EmbeddedStorageManager.Default embeddedStorageManager;

  public EmbeddedPlanLimitStorage(final EmbeddedStorageManager.Default embeddedStorageManager) {
    this.embeddedStorageManager = embeddedStorageManager;
  }

  @Override
  public PlanLimitBucket getPlanLimit(final String apiKey) {
    final PlanLimitDataRoot dataRoot = (PlanLimitDataRoot) embeddedStorageManager.root();
    return Optional.ofNullable(dataRoot)
        .map(PlanLimitDataRoot::getPlanLimitBuckets)
        .orElse(new HashSet<>())
        .stream()
        .filter(
            planLimitBucket -> StringUtils.equalsIgnoreCase(apiKey, planLimitBucket.getApiKey()))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean saveOrUpdate(final PlanLimitBucket planLimitBucket) {
    final PlanLimitDataRoot dataRoot = (PlanLimitDataRoot) embeddedStorageManager.root();
    return Optional.ofNullable(dataRoot)
        .map(root -> root.addPlanLimitBucket(planLimitBucket))
        .orElse(false);
  }

}
