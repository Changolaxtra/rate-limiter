package com.changolaxtra.cloud.ratelimiter.data;

import com.changolaxtra.cloud.ratelimiter.core.TokenBucket;
import com.changolaxtra.cloud.ratelimiter.policy.RateLimitPolicy;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApiLimitBucket {

    @EqualsAndHashCode.Include
    private final String apiKey;
    private final TokenBucket tokenBucket;

    public ApiLimitBucket(RateLimitPolicy rateLimitPolicy){
        this.apiKey = rateLimitPolicy.getApiKey();
        this.tokenBucket = new TokenBucket(rateLimitPolicy);
    }

}
