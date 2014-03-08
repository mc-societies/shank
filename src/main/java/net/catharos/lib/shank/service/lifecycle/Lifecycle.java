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
        public <C> void invokeLifecycle(Service<C> service, C context) {
            service.init(context);
        }
    }),
    STARTING(new AnnotationInvoker(Start.class) {
        @Override
        public <C> void invokeLifecycle(Service<C> service, C context) {
            service.start(context);
        }
    }),
    RUNNING(new EmptyInvoker()),
    STOPPING(new AnnotationInvoker(Stop.class) {
        @Override
        public <C> void invokeLifecycle(Service<C> service, C context) {
            service.stop(context);
        }
    }),
    TERMINATED(new EmptyInvoker());

    private ServiceInvoker invoker;

    Lifecycle(ServiceInvoker invoker) {
        this.invoker = invoker;
    }

    public void invoke(Object obj, LifecycleContext context) {
        invoker.invoke(obj, context);
    }

    public static LifecycleTimeline createTimeline() {
        return new LifecycleTimeline(Lifecycle.values());
    }
}
