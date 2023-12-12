package com.changolaxtra.cloud.ratelimiter.configuration;

import com.changolaxtra.cloud.ratelimiter.core.PlanLimitBucket;
import com.changolaxtra.cloud.ratelimiter.exception.RateLimitException;
import com.changolaxtra.cloud.ratelimiter.core.policy.RateLimitPolicy;
import com.changolaxtra.cloud.ratelimiter.storage.PlanLimitStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultPlanLimitInitializer implements InitializingBean {

    private final PlanLimitStorage planLimitStorage;
    private final RateLimiterConfiguration rateLimiterConfiguration;

    public DefaultPlanLimitInitializer(final PlanLimitStorage planLimitStorage,
                                       final RateLimiterConfiguration rateLimiterConfiguration) {
        this.planLimitStorage = planLimitStorage;
        this.rateLimiterConfiguration = rateLimiterConfiguration;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        final RateLimitPolicy defaultPolicy = getDefaultPolicy();
        final boolean isDefaultPolicyCreated = planLimitStorage.saveOrUpdate(new PlanLimitBucket(defaultPolicy));
        if (!isDefaultPolicyCreated) {
            log.error("Default policy wasn't created.");
            throw new RateLimitException("Default policy wasn't created.");
        }
        log.info("Default policy was created correctly: {}", defaultPolicy);
    }

    private RateLimitPolicy getDefaultPolicy() {
        return RateLimitPolicy
                .builder()
                .apiKey(rateLimiterConfiguration.getDefaultApiKey())
                .allowedRequests(rateLimiterConfiguration.getDefaultAllowedRequests())
                .windowSizeInMilliSeconds(rateLimiterConfiguration.getDefaultWindowSizeInMilliSeconds())
                .isUnlimited(rateLimiterConfiguration.isDefaultIsUnlimited())
                .build();
    }
}
