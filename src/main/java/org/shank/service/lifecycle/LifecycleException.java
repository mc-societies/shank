package org.shank.service.lifecycle;

import net.catharos.lib.core.lang.ArgumentException;

/**
 * Represents a LifecycleException
 */
public class LifecycleException extends ArgumentException {

    public LifecycleException() {
        super();
    }

    public LifecycleException(String message, Object... args) {
        super(message, args);
    }

    public LifecycleException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public LifecycleException(Throwable cause) {
        super(cause);
    }
}
