package com.changolaxtra.cloud.ratelimiter.core;

import com.changolaxtra.cloud.ratelimiter.core.policy.RateLimitPolicy;
import lombok.EqualsAndHashCode;

/**
 * Model class to store the relation between an API Key and the {@link RateLimitPolicy}.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PlanLimitBucket {

  @EqualsAndHashCode.Include
  private final String apiKey;
  private final TokenBucket tokenBucket;

  /**
   * Constructor.
   */
  public PlanLimitBucket(final RateLimitPolicy rateLimitPolicy) {
    this.apiKey = rateLimitPolicy.getApiKey();
    this.tokenBucket = new TokenBucket(rateLimitPolicy);
  }

  /**
   * Gets the API Key.
   */
  public String getApiKey() {
    return apiKey;
  }

  /**
   * Verifies if the internal Bucket has available tokens to process the request.
   */
  public boolean isAllowed() {
    return tokenBucket.isAllowed();
  }

}
