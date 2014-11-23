package net.catharos.lib.shank.service.lifecycle;

import net.catharos.lib.shank.service.Service;
import net.catharos.lib.shank.service.lifecycle.annotation.Init;
import net.catharos.lib.shank.service.lifecycle.annotation.Start;
import net.catharos.lib.shank.service.lifecycle.annotation.Stop;
import net.catharos.lib.shank.service.lifecycle.invoker.AnnotationInvoker;
import net.catharos.lib.shank.service.lifecycle.invoker.EmptyInvoker;
import net.catharos.lib.shank.service.lifecycle.invoker.ServiceInvoker;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public enum Lifecycle {
    NEW(new EmptyInvoker()),
    INITIALISING(new AnnotationInvoker(Init.class) {
        @Override
        public <C> void invokeLifecycle(Service<C> service, C context) throws Exception {
            service.init(context);
        }
    }, "Initialising %s...", "init"),
    STARTING(new AnnotationInvoker(Start.class) {
        @Override
        public <C> void invokeLifecycle(Service<C> service, C context) throws Exception {
            service.start(context);
        }
    }, "Starting %s...", "start"),
    RUNNING(new EmptyInvoker()),
    STOPPING(new AnnotationInvoker(Stop.class) {
        @Override
        public <C> void invokeLifecycle(Service<C> service, C context) throws Exception {
            service.stop(context);
        }
    }, "Stopping %s...", "stop"),
    TERMINATED(new EmptyInvoker());

    private ServiceInvoker invoker;
    private String message;
    private final String methodName;

    Lifecycle(ServiceInvoker invoker) {this(invoker, null, null);}

    Lifecycle(ServiceInvoker invoker, @Nullable String message, String methodName) {
        this.invoker = invoker;
        this.message = message;
        this.methodName = methodName;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void invoke(Object obj, LifecycleContext context) throws LifecycleException {
        invoker.invoke(obj, context);
    }

    public static LifecycleTimeline createTimeline() {
        return new LifecycleTimeline(Lifecycle.values());
    }

    public String getMethodName() {
        return methodName;
    }
}
