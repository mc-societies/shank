package net.catharos.lib.shank.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import net.catharos.lib.shank.service.lifecycle.Lifecycle;
import net.catharos.lib.shank.service.lifecycle.LifecycleContext;
import net.catharos.lib.shank.service.lifecycle.LifecycleTimeline;

import java.util.ArrayList;
import java.util.Set;

/**
 * Represents a ServiceController
 */
@Singleton
public class ServiceController {

    private final LifecycleTimeline timeline;
    private final LifecycleContext context;
    private final ArrayList<Object> services = new ArrayList<Object>();

    @Inject
    public ServiceController(LifecycleTimeline timeline, LifecycleContext context, @Named("services") Set<Object> services) {
        this.timeline = timeline;
        this.context = context;

        for (Object service : services) {
            prepare(service);
        }
    }

    public void prepare(Object obj) {
        Lifecycle[] previous = timeline.getPrevious();

        for (Lifecycle lifecycle : previous) {
            lifecycle.invoke(obj, context);
        }

        services.add(obj);
    }

    public void invoke(Lifecycle lifecycle) {
        timeline.setCurrentLifecycle(lifecycle);
        invoke();
    }

    public void invoke() {
        for (Object service : services) {
            timeline.getCurrentLifecycle().invoke(service, context);
        }
    }

    public LifecycleTimeline getTimeline() {
        return timeline;
    }
}
