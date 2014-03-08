package net.catharos.lib.shank.service;

/**
 * Represents a Service
 */
public interface Service<C> {

    void init(C context) throws Exception;

    void start(C context) throws Exception;

    void stop(C context) throws Exception;
}
