package net.catharos.lib.shank.loader.reflect;

import com.google.common.collect.Sets;
import com.google.inject.Module;
import net.catharos.lib.shank.loader.ModuleClassLoader;
import net.catharos.lib.shank.loader.ModuleDirectoryLoader;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

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
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .addUrls(ClasspathHelper.forPackage(prefix, classLoader))
                        .setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner()));

        Set<String> annotated = reflections.getStore().getTypesAnnotatedWith(RegisteredModule.class.getName());
        Set<String> foundModules = reflections.getStore().getSubTypesOf(Module.class.getName());
        Sets.SetView<String> result = Sets.intersection(annotated, foundModules);

        return ReflectionUtils.forNames(result, classLoader);
    }
}
