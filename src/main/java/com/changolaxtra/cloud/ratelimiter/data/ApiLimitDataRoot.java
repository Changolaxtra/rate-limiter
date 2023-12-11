package com.changolaxtra.cloud.ratelimiter.data;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ApiLimitDataRoot {
    private final Set<ApiLimitBucket> apiLimitBuckets;

    public ApiLimitDataRoot() {
        apiLimitBuckets = new HashSet<>();
    }
}
