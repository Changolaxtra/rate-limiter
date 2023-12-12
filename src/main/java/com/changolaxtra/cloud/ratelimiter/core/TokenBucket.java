package com.changolaxtra.cloud.ratelimiter.core;

import com.changolaxtra.cloud.ratelimiter.core.policy.RateLimitPolicy;

public class TokenBucket {

  private final long numberOfRequests;
  private final long windowSizeForRateLimitInMilliSeconds;
  private final long maxBucketSize;
  private final boolean isUnlimited;

  private long nextRefillTime;
  private long numberOfTokenAvailable;

  public TokenBucket(final RateLimitPolicy rateLimitPolicy) {
    this.maxBucketSize = rateLimitPolicy.getAllowedRequests();
    this.numberOfRequests = rateLimitPolicy.getAllowedRequests();
    this.windowSizeForRateLimitInMilliSeconds = rateLimitPolicy.getWindowSizeInMilliSeconds();
    this.isUnlimited = rateLimitPolicy.isUnlimited();
    this.refill();
  }

  public boolean isAllowed() {
    return isUnlimited || processLimitedRequest();
  }

  private boolean processLimitedRequest() {
    refill();
    if (hasTokenAvailable()) {
      this.numberOfTokenAvailable--;
      return true;
    }
    return false;
  }

  private boolean hasTokenAvailable() {
    return this.numberOfTokenAvailable > 0;
  }

  private void refill() {
    if (needsRefill()) {
      this.nextRefillTime = getNextRefillTime();
      this.numberOfTokenAvailable = getOfTokenAvailable();
    }
  }

  private boolean needsRefill() {
    return System.currentTimeMillis() > this.nextRefillTime;
  }

  private long getNextRefillTime() {
    final long lastRefillTime = System.currentTimeMillis();
    return lastRefillTime + this.windowSizeForRateLimitInMilliSeconds;
  }

  private long getOfTokenAvailable() {
    return Math.min(this.maxBucketSize, this.numberOfTokenAvailable + this.numberOfRequests);
  }

}
