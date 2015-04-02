package org.shank.service.lifecycle;


/**
 * Represents a LifecycleException
 */
public class LifecycleException extends Exception {
    public LifecycleException() {
    }

    public LifecycleException(String message) {
        super(message);
    }

    public LifecycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifecycleException(Throwable cause) {
        super(cause);
    }

    public LifecycleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
