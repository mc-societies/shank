package org.shank.loader.reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Represents a RegisteredModule
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
public @interface RegisteredModule {

    String name();
}
