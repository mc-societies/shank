package net.catharos.lib.shank.service;

/**
 * Represents a Service
 */
public interface Service<C> {

    void init(C context);

    void start(C context);

    void stop(C context);
}
