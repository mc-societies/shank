package net.catharos.lib.shank.service.lifecycle.invoker;

import net.catharos.lib.shank.service.lifecycle.LifecycleContext;

/**
 * Represents a EmptyInvoker
 */
public class EmptyInvoker implements ServiceInvoker {

    @Override
    public void invoke(Object obj, LifecycleContext context) {

    }
}
