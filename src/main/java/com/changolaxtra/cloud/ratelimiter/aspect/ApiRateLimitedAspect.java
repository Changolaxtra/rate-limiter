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

/**
 * Aspect component to intercept the @ApiRateLimited annotation.
 */
@Slf4j
@Aspect
@Component
public class ApiRateLimitedAspect {

  private final PlanLimitStorage planLimitStorage;
  private final RateLimiterConfiguration rateLimiterConfiguration;

  /**
   * Constructor.
   */
  public ApiRateLimitedAspect(final PlanLimitStorage planLimitStorage,
      final RateLimiterConfiguration rateLimiterConfiguration) {
    this.planLimitStorage = planLimitStorage;
    this.rateLimiterConfiguration = rateLimiterConfiguration;
  }

  /**
   * Around pointcut to intercept the @ApiRateLimited annotation that verifies if there are tokens
   * available to proceed with the request.
   *
   * @return the normal result of the method or HTTP 403 Entity if there are no tokens available.
   */
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
