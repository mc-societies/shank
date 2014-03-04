package net.catharos.lib.shank.service;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import java.util.concurrent.Future;

/**
 * Represents a Service
 */
public interface Service {

    void init();

    void start();

    void stop();

    Future initService();

    Future startService();

    Future stopService();

    <T> T get(Class<T> type);

    <T> T get(Key<T> key);

    <T> T get(TypeLiteral<T> literal);

    <T> T getNamed(Class<T> type, String name);
}
