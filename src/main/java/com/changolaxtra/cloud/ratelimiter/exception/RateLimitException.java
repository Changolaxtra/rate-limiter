package com.changolaxtra.cloud.ratelimiter.exception;

/**
 * {@link RuntimeException} for rate limit errors.
 */
public class RateLimitException extends RuntimeException {

  public RateLimitException(final String message) {
    super(message);
  }
}
