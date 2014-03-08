package net.catharos.lib.shank.service.lifecycle.invoker;

import net.catharos.lib.shank.service.lifecycle.LifecycleContext;

/**
 * Represents a ServiceInvoker
 */
public interface ServiceInvoker {

    void invoke(Object obj, LifecycleContext context);
}
