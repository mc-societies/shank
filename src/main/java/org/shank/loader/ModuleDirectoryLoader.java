package org.shank.loader;


import org.shank.ModuleDescription;
import org.shank.loader.reflect.RegisteredModule;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.security.AccessController.doPrivileged;

/**
 * Represents a ModuleDirectoryLoader
 */
public abstract class ModuleDirectoryLoader extends ModuleLoader {
    private static final ExtensionFilter ARCHIVE_FILTER = new ExtensionFilter("jar", "zip", "plugin");

    public ModuleDirectoryLoader(ClassLoader defaultParent, File modulesDirectory) {
        super(defaultParent, modulesDirectory);
    }

    @Override
    public Set<ModuleDescription> loadDescriptions() throws ModuleLoadingException {
        File[] files = getModulesDirectory().listFiles(ARCHIVE_FILTER);

        if (files == null) {
            return Collections.emptySet();
        }

        Set<ModuleDescription> modules = new HashSet<ModuleDescription>();

        for (File path : files) {
            String absolutePath = path.getAbsolutePath();

            final URL url;

            try {
                url = path.toURI().toURL();
            } catch (MalformedURLException ignored) {
                throw new ModuleLoadingException(null, "Could not create an url of the file " + absolutePath + "!");
            }

            ModuleClassLoader moduleClassLoader = doPrivileged(new PrivilegedAction<ModuleClassLoader>() {
                @Override
                public ModuleClassLoader run() {
                    return new ModuleClassLoader(getParentClassLoader(), url);
                }
            });

            for (Class<?> moduleClass : getModuleClasses(url, moduleClassLoader)) {
                RegisteredModule annotation = moduleClass.getAnnotation(RegisteredModule.class);
                String name = annotation.name();

                ModuleDescription<URL> description = new ModuleDescription<URL>(name, moduleClass, url, moduleClassLoader);
                modules.add(description);

                moduleClassLoader.setModule(description);
            }
        }
        return modules;
    }

    protected abstract Iterable<Class<?>> getModuleClasses(URL url, ModuleClassLoader classLoader);
}
