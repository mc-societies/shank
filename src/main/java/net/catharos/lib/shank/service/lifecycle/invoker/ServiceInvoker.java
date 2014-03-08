package net.catharos.lib.shank.service.lifecycle.invoker;

import net.catharos.lib.shank.service.lifecycle.LifecycleContext;
import net.catharos.lib.shank.service.lifecycle.LifecycleException;

/**
 * Represents a ServiceInvoker
 */
public interface ServiceInvoker {

    void invoke(Object obj, LifecycleContext context) throws LifecycleException;
}
