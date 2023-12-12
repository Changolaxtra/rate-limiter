package com.changolaxtra.cloud.ratelimiter.exception;

public class RateLimitException extends RuntimeException {

    public RateLimitException(final String message) {
        super(message);
    }
}
