package com.changolaxtra.cloud.ratelimiter.core;

import com.changolaxtra.cloud.ratelimiter.core.policy.RateLimitPolicy;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PlanLimitBucket {

  @EqualsAndHashCode.Include
  private final String apiKey;
  private final TokenBucket tokenBucket;

  public PlanLimitBucket(final RateLimitPolicy rateLimitPolicy) {
    this.apiKey = rateLimitPolicy.getApiKey();
    this.tokenBucket = new TokenBucket(rateLimitPolicy);
  }

  public String getApiKey() {
    return apiKey;
  }

  public boolean isAllowed() {
    return tokenBucket.isAllowed();
  }

}
