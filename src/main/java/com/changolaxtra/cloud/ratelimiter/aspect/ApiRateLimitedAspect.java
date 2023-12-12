package com.changolaxtra.cloud.ratelimiter.aspect;

import com.changolaxtra.cloud.ratelimiter.configuration.RateLimiterConfiguration;
import com.changolaxtra.cloud.ratelimiter.core.PlanLimitBucket;
import com.changolaxtra.cloud.ratelimiter.storage.PlanLimitStorage;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class ApiRateLimitedAspect {

  private final PlanLimitStorage planLimitStorage;
  private final RateLimiterConfiguration rateLimiterConfiguration;

  public ApiRateLimitedAspect(final PlanLimitStorage planLimitStorage,
      final RateLimiterConfiguration rateLimiterConfiguration) {
    this.planLimitStorage = planLimitStorage;
    this.rateLimiterConfiguration = rateLimiterConfiguration;
  }

  @Around("@annotation(com.changolaxtra.cloud.ratelimiter.annotations.ApiRateLimited)")
  public Object rateLimitCheck(final ProceedingJoinPoint joinPoint) throws Throwable {
    final String apiKey = getApiKey();
    final PlanLimitBucket planLimit = planLimitStorage.getPlanLimit(apiKey);
    if (planLimit.isAllowed()) {
      return joinPoint.proceed();
    } else {
      return ResponseEntity
          .status(HttpStatus.TOO_MANY_REQUESTS)
          .body("API-Keys does not allow more calls at this moment.");
    }
  }

  private String getApiKey() {
    return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
        .map(requestAttributes -> (ServletRequestAttributes) requestAttributes)
        .map(ServletRequestAttributes::getRequest)
        .map(request -> request.getHeader(rateLimiterConfiguration.getApiKeyHeaderName()))
        .orElse(rateLimiterConfiguration.getDefaultApiKey());
  }
}
