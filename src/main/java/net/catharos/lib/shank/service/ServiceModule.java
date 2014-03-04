package net.catharos.lib.shank.service;

import com.google.inject.Binder;
import com.google.inject.Module;
import net.catharos.lib.shank.service.lifecycle.Lifecycle;
import net.catharos.lib.shank.service.lifecycle.LifecycleTimeline;

import java.util.concurrent.ExecutorService;

/**
 * Represents a ServiceModule
 */
public class ServiceModule implements Module {

    private final ExecutorService executorService;

    public ServiceModule(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(LifecycleTimeline.class).toInstance(Lifecycle.createTimeline());
        binder.bind(ServiceController.class);

        binder.bind(ExecutorService.class).annotatedWith(ServiceExecutor.class).toInstance(executorService);
    }

    public void stop() {
        executorService.shutdown();
    }
}
