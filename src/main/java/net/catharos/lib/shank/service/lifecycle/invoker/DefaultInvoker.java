package net.catharos.lib.shank.service.lifecycle.invoker;

import net.catharos.lib.shank.service.Service;

/**
 * Represents a DefaultInvoker
 */
public abstract class DefaultInvoker implements ServiceInvoker {

    @Override
    public void invoke(Object obj) {
        if (obj instanceof Service) {
            Service service = (Service) obj;

            invoke(service);
        }
    }

    public abstract void invoke(Service service);
}
