package net.catharos.lib.shank;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import net.catharos.lib.core.util.CastSafe;
import net.catharos.lib.shank.loader.ModuleLoader;
import net.catharos.lib.shank.loader.ModuleLoadingException;
import net.catharos.lib.shank.service.lifecycle.Lifecycle;
import net.catharos.lib.shank.service.lifecycle.LifecycleTimeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a InjectorBuilder
 */
public class InjectorBuilder {

    private List<Module> modules = new ArrayList<Module>();
    private LifecycleTimeline timeline;
    private final Stage stage;

    public InjectorBuilder(Module... otherModules) {
        this(Stage.DEVELOPMENT, otherModules);
    }

    public InjectorBuilder(Stage stage, Module... otherModules) {
        this.stage = stage;
        Collections.addAll(modules, otherModules);
        timeline = Lifecycle.createTimeline();
    }

    public InjectorBuilder loadModules(ModuleLoader loader) throws ModuleLoadingException {
        loadModules(loader.loadModules());
        return this;
    }

    public InjectorBuilder loadModules(Iterable<Module> modules) {
        for (Module module : modules) {
            loadModule(module);
        }

        return this;
    }

    public InjectorBuilder loadModule(Module module) {
        modules.add(module);
        return this;
    }

    public List<Module> getModulesList() {
        return Collections.unmodifiableList(modules);
    }

    public Module[] getModules() {
        return modules.toArray(new Module[modules.size()]);
    }

    public Injector createInjector() {
        return Guice.createInjector(stage, modules);
    }

    public LifecycleTimeline getTimeline() {
        return timeline;
    }

    public void clear() {
        this.modules.clear();
    }

    public <M extends Module> M get(Class<? extends M> type) {
        for (Module module : modules) {
            if (module.getClass() == type) {
                return CastSafe.toGeneric(module);
            }
        }

        return null;
    }
}
