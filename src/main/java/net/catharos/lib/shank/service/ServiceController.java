package net.catharos.lib.shank.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import net.catharos.lib.shank.service.lifecycle.Lifecycle;
import net.catharos.lib.shank.service.lifecycle.LifecycleTimeline;

import java.util.ArrayList;
import java.util.Set;

/**
 * Represents a ServiceController
 */
@Singleton
public class ServiceController  {

    private final LifecycleTimeline timeline;
    private final ArrayList<Object> services = new ArrayList<Object>();

    @Inject
    public ServiceController(LifecycleTimeline timeline, @Named("services") Set<Object> services) {
        this.timeline = timeline;

        for (Object service : services) {
            prepare(service);
        }
    }

    public void prepare(Object obj) {
        Lifecycle[] previous = timeline.getPrevious();

        for (Lifecycle lifecycle : previous) {
            lifecycle.getInvoker().invoke(obj);
        }

        services.add(obj);
    }

    public void invoke() {
        for (Object service : services) {
            timeline.getCurrentLifecycle().invoke(service);
        }
    }

    public LifecycleTimeline getTimeline() {
        return timeline;
    }
}
