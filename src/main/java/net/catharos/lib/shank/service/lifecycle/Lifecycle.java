package net.catharos.lib.shank.service.lifecycle;

import net.catharos.lib.shank.service.Service;
import net.catharos.lib.shank.service.lifecycle.annotation.Init;
import net.catharos.lib.shank.service.lifecycle.annotation.Start;
import net.catharos.lib.shank.service.lifecycle.annotation.Stop;
import net.catharos.lib.shank.service.lifecycle.invoker.AnnotationInvoker;
import net.catharos.lib.shank.service.lifecycle.invoker.EmptyInvoker;
import net.catharos.lib.shank.service.lifecycle.invoker.ServiceInvoker;

/**
 *
 */
public enum Lifecycle {
    NEW(new EmptyInvoker()),
    INITIALISING(new AnnotationInvoker(Init.class) {
        @Override
        public void invoke(Service service) {
            service.init();
        }
    }),
    STARTING(new AnnotationInvoker(Start.class) {
        @Override
        public void invoke(Service service) {
            service.start();
        }
    }),
    RUNNING(new EmptyInvoker()),
    STOPPING(new AnnotationInvoker(Stop.class) {
        @Override
        public void invoke(Service service) {
            service.stop();
        }
    }),
    TERMINATED(new EmptyInvoker());

    private ServiceInvoker invoker;

    Lifecycle(ServiceInvoker invoker) {
        this.invoker = invoker;
    }

    public ServiceInvoker getInvoker() {
        return invoker;
    }

    public void invoke(Object obj) {
        getInvoker().invoke(obj);
    }

    public static LifecycleTimeline createTimeline() {
        return new LifecycleTimeline(Lifecycle.values());
    }
}
