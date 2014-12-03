package org.shank.service.lifecycle;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

/**
 * Represents a LifecycleContext
 */
public class LifecycleContext {

    @Inject
    private Injector injector;

    public final Injector injector() {
        return injector;
    }

    public final <T> T get(Class<T> type) {
        return injector().getInstance(type);
    }

    public final <T> T get(Key<T> key) {
        return injector().getInstance(key);
    }

    public final <T> T get(TypeLiteral<T> literal) {
        return get(Key.get(literal));
    }

    public final <T> T getNamed(Class<T> type, String name) {
        return injector().getInstance(Key.get(type, Names.named(name)));
    }

    public final <T> T getNamed(TypeLiteral<T> type, String name) {
        return injector().getInstance(Key.get(type, Names.named(name)));
    }
}
