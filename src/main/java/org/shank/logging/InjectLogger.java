package org.shank.logging;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 */
@Retention(value = RUNTIME)
@Target(value = FIELD)
public @interface InjectLogger {

    String name() default "";
}
