package org.shank.service.lifecycle.invoker;

import org.shank.service.lifecycle.LifecycleContext;

/**
 * Represents a EmptyInvoker
 */
public class EmptyInvoker implements ServiceInvoker {

    @Override
    public void invoke(Object obj, LifecycleContext context) {

    }
}
