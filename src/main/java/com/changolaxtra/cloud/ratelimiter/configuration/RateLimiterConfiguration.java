package com.changolaxtra.cloud.ratelimiter.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class RateLimiterConfiguration {

  @Value("${rate-limiter.apiKey-header-name}")
  private String apiKeyHeaderName;

  @Value("${rate-limiter.default-policy.apiKey}")
  private String defaultApiKey;

  @Value("${rate-limiter.default-policy.allowedRequests}")
  private long defaultAllowedRequests;

  @Value("${rate-limiter.default-policy.windowSizeInMilliSeconds}")
  private long defaultWindowSizeInMilliSeconds;

  @Value("${rate-limiter.default-policy.isUnlimited}")
  private boolean defaultIsUnlimited;

}
