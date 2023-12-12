package com.changolaxtra.cloud.ratelimiter.annotations;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ComponentScan(basePackages = "com.changolaxtra.cloud.ratelimiter")
public @interface EnableApiRateLimiter {
}
