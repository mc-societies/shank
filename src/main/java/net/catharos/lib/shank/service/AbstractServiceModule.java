package net.catharos.lib.shank.service;

import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import net.catharos.lib.shank.AbstractModule;

/**
 * Represents a ServiceModuleAdapter
 */
public abstract class AbstractServiceModule extends AbstractModule {

    protected LinkedBindingBuilder<Object> bindService() {
        Multibinder<Object> services = Multibinder.newSetBinder(binder(), Object.class, Names.named("services"));
        LinkedBindingBuilder<Object> binding = services.addBinding();
        binding.asEagerSingleton();
        return binding;
    }
}
