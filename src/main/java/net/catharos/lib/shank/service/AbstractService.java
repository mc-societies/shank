package net.catharos.lib.shank.service;

import net.catharos.lib.shank.service.lifecycle.LifecycleContext;

/**
 * Represents a ServiceAdapter
 */
public class AbstractService<C extends LifecycleContext> implements Service<C> {

    @Override
    public void init(C context) throws Exception {}

    @Override
    public void start(C context) throws Exception {}

    @Override
    public void stop(C context) throws Exception {}
}
