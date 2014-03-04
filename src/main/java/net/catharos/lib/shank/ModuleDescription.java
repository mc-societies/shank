package net.catharos.lib.shank;

import net.catharos.lib.shank.loader.ModuleClassLoader;

/** Represents a ModuleDescription */
public class ModuleDescription<S> {
    private final String name;
    private final Class<?> moduleClass;
    private final S source;

    private ModuleClassLoader classLoader;

    public ModuleDescription(String name, Class<?> moduleClass, S source, ModuleClassLoader classLoader) {
        this.name = name;
        this.moduleClass = moduleClass;
        this.source = source;
        this.classLoader = classLoader;
    }

    /** @return The name of the plugin */
    public String getName() {
        return name;
    }

    /**
     * @return The main class of the plugin
     * @throws ClassNotFoundException If the main class is not found
     */
    public Class<?> getModuleClass() throws ClassNotFoundException {
        return moduleClass;
    }

    public ModuleClassLoader getClassLoader() {
        return classLoader;
    }

    /** @return The source of this plugin */
    public S getSource() {
        return source;
    }
}
