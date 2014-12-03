package org.shank.loader.reflect;

import com.google.common.collect.Sets;
import com.google.inject.Module;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.shank.loader.ModuleClassLoader;
import org.shank.loader.ModuleDirectoryLoader;

import java.io.File;
import java.net.URL;
import java.util.Set;

/**
 * Represents a ModuleDirectoryLoader
 */

public class ModuleReflectionLoader extends ModuleDirectoryLoader {

    private final String prefix;

    public ModuleReflectionLoader(ClassLoader defaultParent, File modulesDirectory, String prefix) {
        super(defaultParent, modulesDirectory);
        this.prefix = prefix;
    }

    @Override
    protected Iterable<Class<?>> getModuleClasses(URL url, ModuleClassLoader classLoader) {
        ConfigurationBuilder builder = new ConfigurationBuilder()
                .addUrls(ClasspathHelper.forPackage(prefix, classLoader))
                .setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner());

        Reflections reflections = new Reflections(builder);

        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(RegisteredModule.class);
        Set<Class<? extends Module>> foundModules = reflections.getSubTypesOf(Module.class);

        return Sets.intersection(annotated, foundModules);
    }
}
