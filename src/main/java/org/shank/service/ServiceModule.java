package org.shank.service;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.shank.service.lifecycle.Lifecycle;
import org.shank.service.lifecycle.LifecycleTimeline;

/**
 * Represents a ServiceModule
 */
public class ServiceModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(LifecycleTimeline.class).toInstance(Lifecycle.createTimeline());
        binder.bind(ServiceController.class);
    }
}
