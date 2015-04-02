package org.shank;

import com.google.inject.PrivateModule;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.name.Names;

/**
 * Represents a AbstractPrivateModule
 */
public abstract class AbstractPrivateModule extends PrivateModule {
    protected <T> LinkedBindingBuilder<T> bindNamed(String name, Class<T> clazz) {
        return bindNamed(name, TypeLiteral.get(clazz));
    }

    protected <T> LinkedBindingBuilder<T> bindNamed(String name, TypeLiteral<T> literal) {
        return binder().bind(literal).annotatedWith(Names.named(name));
    }

    protected <T> void bindNamedInstance(String name, T instance) {
        Class<T> type = (Class<T>) instance.getClass();
        bindNamedInstance(name, type, instance);
    }

    protected <T> void bindNamedInstance(String name, Class<T> clazz, T instance) {
        bindNamed(name, clazz).toInstance(instance);
    }

    protected <T> void bindNamedInstance(String name, TypeLiteral<T> literal, T instance) {
        bindNamed(name, literal).toInstance(instance);
    }

    protected void bindNamedString(String name, String string) {
        bindNamedInstance(name, String.class, string);
    }
}
