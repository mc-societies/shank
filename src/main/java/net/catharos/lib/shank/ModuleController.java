package net.catharos.lib.shank;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import net.catharos.lib.shank.loader.ModuleLoader;
import net.catharos.lib.shank.loader.ModuleLoadingException;
import net.catharos.lib.shank.service.lifecycle.Lifecycle;
import net.catharos.lib.shank.service.lifecycle.LifecycleTimeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a ModuleController
 */
public class ModuleController {

    private List<Module> modules = new ArrayList<>();
    private LifecycleTimeline timeline;
    private final Stage stage;

    public ModuleController(Module... otherModules) {
        this(Stage.DEVELOPMENT, otherModules);
    }

    public ModuleController(Stage stage, Module... otherModules) {
        this.stage = stage;
        Collections.addAll(modules, otherModules);
        timeline = Lifecycle.createTimeline();
    }

    public void loadModules(ModuleLoader loader) throws ModuleLoadingException {
        loadModules(loader.loadModules());
    }

    public void loadModules(Iterable<Module> modules) {
        for (Module module : modules) {
            loadModule(module);
        }
    }

    public void loadModule(Module module) {
        modules.add(module);
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
}
