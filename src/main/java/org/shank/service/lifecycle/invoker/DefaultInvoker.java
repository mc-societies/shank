package org.shank.service.lifecycle.invoker;

import org.shank.service.Service;
import org.shank.service.lifecycle.LifecycleContext;
import org.shank.service.lifecycle.LifecycleException;

/**
 * Represents a DefaultInvoker
 */
public abstract class DefaultInvoker implements ServiceInvoker {

    @Override
    public void invoke(Object obj, LifecycleContext context) throws LifecycleException {
        if (obj instanceof Service) {
            Service service = (Service) obj;

            try {
                invokeLifecycle(service, context);
            } catch (Exception e) {
                throw new LifecycleException(e);
            }
        }
    }

    public abstract <C> void invokeLifecycle(Service<C> service, C context) throws Exception;

}
