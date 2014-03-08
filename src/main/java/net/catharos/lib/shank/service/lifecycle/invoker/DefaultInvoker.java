package net.catharos.lib.shank.service.lifecycle.invoker;

import net.catharos.lib.shank.service.Service;
import net.catharos.lib.shank.service.lifecycle.LifecycleContext;

/**
 * Represents a DefaultInvoker
 */
public abstract class DefaultInvoker implements ServiceInvoker {

    @Override
    public void invoke(Object obj, LifecycleContext context) {
        if (obj instanceof Service) {
            Service service = (Service) obj;

            invokeLifecycle(service, context);
        }
    }

    public abstract <C> void invokeLifecycle(Service<C> service, C context);
}
