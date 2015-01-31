package org.shank.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.logging.log4j.Logger;
import org.shank.service.lifecycle.Lifecycle;
import org.shank.service.lifecycle.LifecycleContext;
import org.shank.service.lifecycle.LifecycleException;
import org.shank.service.lifecycle.LifecycleTimeline;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Set;

/**
 * Represents a ServiceController
 */
@Singleton
public class ServiceController {

    private final Logger logger;
    private final LifecycleTimeline timeline;
    private final LifecycleContext context;
    private final ArrayList<Object> services = new ArrayList<Object>();
    private final UncaughtExceptionHandler exceptionHandler;

    @Inject
    public ServiceController(@Named("service-logger") Logger logger,
                             LifecycleTimeline timeline,
                             LifecycleContext context,
                             @Named("services") Set<Object> services,
                             UncaughtExceptionHandler exceptionHandler) {
        this.logger = logger;
        this.timeline = timeline;
        this.context = context;
        this.exceptionHandler = exceptionHandler;

        for (Object service : services) {
            prepare(service);
        }
    }

    public void prepare(Object service) {
        Lifecycle[] previous = timeline.getPrevious();

        for (Lifecycle lifecycle : previous) {
            try {
                lifecycleInfo(lifecycle, service);
                lifecycle.invoke(service, context);
            } catch (LifecycleException e) {
                lifecycleFailed(lifecycle, e, service);
            }
        }

        services.add(service);
    }

    public void invoke(Lifecycle lifecycle) {
        timeline.setCurrentLifecycle(lifecycle);
        invoke();
    }

    public void invoke() {
        for (Object service : services) {
            Lifecycle lifecycle = timeline.getCurrentLifecycle();

            try {
                lifecycleInfo(lifecycle, service);
                lifecycle.invoke(service, context);
            } catch (LifecycleException e) {
                lifecycleFailed(lifecycle, e, service);
            }
        }
    }

    private void lifecycleInfo(Lifecycle lifecycle, Object service) {
        if (logger != null && lifecycle.getMessage() != null) {

            try {
                String name = lifecycle.getMethodName();

                if (name != null) {
                    service.getClass().getMethod(name, Object.class);
                }
            } catch (NoSuchMethodException ignored) {
                return;
            }

            String name = service.getClass().getSimpleName();
            logger.info(String.format(lifecycle.getMessage(), name));
        }
    }

    private void lifecycleFailed(Lifecycle lifecycle, Throwable throwable, Object service) {
        if (logger != null) {
            logger.fatal("Exception occurred while running lifecycle " + lifecycle + " of " + service);
        }
        exceptionHandler.uncaughtException(Thread.currentThread(), throwable);
    }

    public LifecycleTimeline getTimeline() {
        return timeline;
    }
}
