package net.catharos.lib.shank.service.lifecycle.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 */
@Documented
@Retention(value = RUNTIME)
@Target(value = METHOD)
public @interface Start {
}
