package org.shank.service.lifecycle.invoker;

import org.shank.service.lifecycle.LifecycleContext;
import org.shank.service.lifecycle.LifecycleException;

/**
 * Represents a ServiceInvoker
 */
public interface ServiceInvoker {

    void invoke(Object obj, LifecycleContext context) throws LifecycleException;
}
