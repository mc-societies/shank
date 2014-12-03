package org.shank.loader;

import com.google.inject.Module;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.shank.ModuleDescription;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Used to load modules
 */
public abstract class ModuleLoader {

    private final ClassLoader defaultParent;
    private final File modulesDirectory;

    public ModuleLoader(ClassLoader defaultParent, File modulesDirectory) {
        this.defaultParent = defaultParent;
        this.modulesDirectory = modulesDirectory;
    }

    public List<Module> loadModules() throws ModuleLoadingException {
        Set<ModuleDescription> descriptions = loadDescriptions();

        List<Module> modules = new ArrayList<Module>(descriptions.size());

        for (ModuleDescription description : descriptions) {
            modules.add(load(description));
        }

        return modules;
    }

    public Module load(ModuleDescription description) throws ModuleLoadingException {
        try {
            Class<?> moduleClass = description.getModuleClass();
            Object obj = ConstructorUtils.invokeConstructor(moduleClass);

            if (!(obj instanceof Module)) {
                throw new ModuleLoadingException(null,
                        "The module class "
                                + moduleClass.getSimpleName()
                                + " is not an instance of " + Module.class.getSimpleName()
                );
            }

            return (Module) obj;
        } catch (Exception e) {
            throw new ModuleLoadingException(null, e);
        }
    }

    public abstract Set<ModuleDescription> loadDescriptions() throws ModuleLoadingException;

    public ClassLoader getParentClassLoader() {
        return defaultParent;
    }

    protected File getModulesDirectory() {
        return modulesDirectory;
    }

    public File getModuleDirectory(String name) throws IOException {
        return new File(modulesDirectory, name);
    }
}
