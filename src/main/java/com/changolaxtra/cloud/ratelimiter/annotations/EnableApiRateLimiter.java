package com.changolaxtra.cloud.ratelimiter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.ComponentScan;

/**
 * Annotation used to enable the Component Scan of the Beans for the library package.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ComponentScan(basePackages = "com.changolaxtra.cloud.ratelimiter")
public @interface EnableApiRateLimiter {

}
