package net.catharos.lib.shank;

import com.google.inject.*;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.AnnotatedConstantBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.matcher.Matcher;
import com.google.inject.name.Names;
import com.google.inject.spi.TypeConverter;
import com.google.inject.spi.TypeListener;
import net.catharos.lib.core.util.CastSafe;

import java.lang.annotation.Annotation;

public abstract class AbstractModule implements Module {

    private Binder binder;

    protected <T> LinkedBindingBuilder<T> bindNamed(String name, Class<T> clazz) {
        return bindNamed(name, TypeLiteral.get(clazz));
    }

    protected <T> LinkedBindingBuilder<T> bindNamed(String name, TypeLiteral<T> literal) {
        return binder().bind(literal).annotatedWith(Names.named(name));
    }

    protected <T> void bindNamedInstance(String name, T instance) {
        Class<T> type = CastSafe.toGeneric(instance.getClass());
        bindNamedInstance(name, type, instance);
    }

    protected <T> void bindNamedInstance(String name, Class<T> clazz, T instance) {
        bindNamed(name, clazz).toInstance(instance);
    }

    protected void bindNamedString(String name, String string) {
        bindNamedInstance(name, String.class, string);
    }

    @Override
    public void configure(Binder binder) {
        try {
            this.binder = binder;
            configure();
        } finally {
            this.binder = null;
        }
    }

    /**
     * Configures a {@link Binder} via the exposed methods.
     */
    protected abstract void configure();

    /**
     * Gets direct access to the underlying {@code Binder}.
     */
    protected Binder binder() {
        if (binder == null) {
            throw new IllegalStateException("Binding process already finished!");
        }
        return binder;
    }

    /**
     * @see Binder#bindScope(Class, com.google.inject.Scope)
     */
    protected void bindScope(Class<? extends Annotation> scopeAnnotation, Scope scope) {
        binder().bindScope(scopeAnnotation, scope);
    }

    /**
     * @see Binder#bind(Key)
     */
    protected <T> LinkedBindingBuilder<T> bind(Key<T> key) {
        return binder().bind(key);
    }

    /**
     * @see Binder#bind(TypeLiteral)
     */
    protected <T> AnnotatedBindingBuilder<T> bind(TypeLiteral<T> typeLiteral) {
        return binder().bind(typeLiteral);
    }

    /**
     * @see Binder#bind(Class)
     */
    protected <T> AnnotatedBindingBuilder<T> bind(Class<T> clazz) {
        return binder().bind(clazz);
    }

    /**
     * @see Binder#bindConstant()
     */
    protected AnnotatedConstantBindingBuilder bindConstant() {
        return binder().bindConstant();
    }

    /**
     * @see Binder#install(Module)
     */
    protected void install(Module module) {
        binder().install(module);
    }

    /**
     * @see Binder#requestInjection(Object)
     */
    protected void requestInjection(Object instance) {
        binder().requestInjection(instance);
    }

    /**
     * @see Binder#requestStaticInjection(Class[])
     */
    protected void requestStaticInjection(Class<?>... types) {
        binder().requestStaticInjection(types);
    }

    /**
     * Adds a dependency from this module to {@code key}. When the injector is
     * created, Guice will report an error if {@code key} cannot be injected.
     * Note that this requirement may be satisfied by implicit binding, such as
     * a public no-arguments constructor.
     */
    protected void requireBinding(Key<?> key) {
        binder().getProvider(key);
    }

    /**
     * Adds a dependency from this module to {@code type}. When the injector is
     * created, Guice will report an error if {@code type} cannot be injected.
     * Note that this requirement may be satisfied by implicit binding, such as
     * a public no-arguments constructor.
     */
    protected void requireBinding(Class<?> type) {
        binder().getProvider(type);
    }

    /**
     * @see Binder#getProvider(Key)
     */
    protected <T> Provider<T> getProvider(Key<T> key) {
        return binder().getProvider(key);
    }

    /**
     * @see Binder#getProvider(Class)
     */
    protected <T> Provider<T> getProvider(Class<T> type) {
        return binder().getProvider(type);
    }

    /**
     * @see Binder#convertToTypes
     */
    protected void convertToTypes(Matcher<? super TypeLiteral<?>> typeMatcher, TypeConverter converter) {
        binder().convertToTypes(typeMatcher, converter);
    }

    /**
     * @see Binder#currentStage()
     */
    protected Stage currentStage() {
        return binder().currentStage();
    }

    /**
     * @see Binder#getMembersInjector(Class)
     */
    protected <T> MembersInjector<T> getMembersInjector(Class<T> type) {
        return binder().getMembersInjector(type);
    }

    /**
     * @see Binder#getMembersInjector(TypeLiteral)
     */
    protected <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> type) {
        return binder().getMembersInjector(type);
    }

    /**
     * @see Binder#bindListener(com.google.inject.matcher.Matcher, com.google.inject.spi.TypeListener)
     */
    protected void bindListener(Matcher<? super TypeLiteral<?>> typeMatcher, TypeListener listener) {
        binder().bindListener(typeMatcher, listener);
    }
}
