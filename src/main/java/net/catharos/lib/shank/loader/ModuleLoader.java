package net.catharos.lib.shank.loader;

import com.google.inject.Module;
import net.catharos.engine.core.filesystem.Directory;
import net.catharos.lib.shank.ModuleDescription;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Used to load modules
 */
public abstract class ModuleLoader {
    public static final String PARENT_CLASSLOADER = "parent-classloader";

    private final ClassLoader defaultParent;
    private final Directory modulesDirectory;

    public ModuleLoader(ClassLoader defaultParent, Directory modulesDirectory) {
        this.defaultParent = defaultParent;
        this.modulesDirectory = modulesDirectory;
    }

    public List<Module> loadModules() throws ModuleLoadingException {
        Set<ModuleDescription> descriptions = loadDescriptions();

        List<Module> modules = new ArrayList<>(descriptions.size());

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

    protected Directory getModulesDirectory() {
        return modulesDirectory;
    }

    public Directory getModuleDirectory(String name) throws IOException {
        return Directory.createDirectory(modulesDirectory, name);
    }

//    private static TopologicalSortedList.Node<ModuleDescription> findModuleDescription0(TopologicalSortedList<ModuleDescription> descs, String name) {
//        for (TopologicalSortedList.Node<ModuleDescription> desc : descs) {
//            if (desc.getValue().getName().equals(name)) {
//                return desc;
//            }
//        }
//
//        return null;
//    }
}
