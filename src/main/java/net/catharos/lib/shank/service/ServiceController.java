package net.catharos.lib.shank.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import net.catharos.lib.shank.service.lifecycle.Lifecycle;
import net.catharos.lib.shank.service.lifecycle.LifecycleContext;
import net.catharos.lib.shank.service.lifecycle.LifecycleException;
import net.catharos.lib.shank.service.lifecycle.LifecycleTimeline;
import org.apache.logging.log4j.Logger;

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

    public void prepare(Object obj) {
        Lifecycle[] previous = timeline.getPrevious();

        for (Lifecycle lifecycle : previous) {
            try {
                lifecycle.invoke(obj, context);
            } catch (LifecycleException e) {
                if (logger != null) {
                    logger.fatal("Exception occurred while running lifecycle" + lifecycle + " of " + obj);
                }
                exceptionHandler.uncaughtException(Thread.currentThread(), e);
            }
        }

        services.add(obj);
    }

    public void invoke(Lifecycle lifecycle) {
        timeline.setCurrentLifecycle(lifecycle);
        invoke();
    }

    public void invoke() {
        for (Object service : services) {
            Lifecycle lifecycle = timeline.getCurrentLifecycle();

            try {
                lifecycle.invoke(service, context);
            } catch (LifecycleException e) {
                if (logger != null) {
                    logger.fatal("Exception occurred while running lifecycle" + lifecycle + " of " + service);
                }
                exceptionHandler.uncaughtException(Thread.currentThread(), e);
            }
        }
    }

    public LifecycleTimeline getTimeline() {
        return timeline;
    }
}
