package com.changolaxtra.cloud.ratelimiter.policy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RateLimitPolicy {
    private String apiKey;
    private long allowedRequests;
    private long windowSizeInMilliSeconds;
    private boolean isUnlimited;
}
