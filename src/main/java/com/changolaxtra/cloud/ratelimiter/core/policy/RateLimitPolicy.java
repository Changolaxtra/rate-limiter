package com.changolaxtra.cloud.ratelimiter.core.policy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class RateLimitPolicy {
    private String apiKey;
    private long allowedRequests;
    private long windowSizeInMilliSeconds;
    private boolean isUnlimited;
}
