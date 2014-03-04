package net.catharos.lib.shank.loader;

import net.catharos.lib.core.util.ExtensionFilter;
import net.catharos.lib.shank.ModuleDescription;
import net.catharos.lib.shank.loader.reflect.RegisteredModule;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a ModuleDirectoryLoader
 */
public abstract class ModuleDirectoryLoader extends ModuleLoader {
    private static final ExtensionFilter ARCHIVE_FILTER = new ExtensionFilter("jar", "zip", "plugin");
//    private final ObjectMapper mapper = new ObjectMapper();

    public ModuleDirectoryLoader(ClassLoader defaultParent, File modulesDirectory) {
        super(defaultParent, modulesDirectory);
    }

    @Override
    public Set<ModuleDescription> loadDescriptions() throws ModuleLoadingException {
        Set<ModuleDescription> modules = new HashSet<ModuleDescription>();

        for (File path : getModulesDirectory().listFiles(ARCHIVE_FILTER)) {
            String absolutePath = path.getAbsolutePath();

//                ZipFile zf;
//                try {
//                    zf = new ZipFile(absolutePath);
//                } catch (IOException e) {
//                    throw new ModuleLoadingException(null, "Failed to open " + absolutePath + "!");
//                }

//                ZipEntry entry = zf.getEntry("module.json");
//
//                JsonNode moduleConfig;
//                try {
//                    moduleConfig = mapper.readTree(zf.getInputStream(entry));
//                } catch (IOException e) {
//                    throw new ModuleLoadingException(null, e);
//                }
//
//
//                JsonNode modulesClassesJson = moduleConfig.get("modules");

            URL url;

            try {
                url = path.toURI().toURL();
            } catch (MalformedURLException e) {
                throw new ModuleLoadingException(null, "Could not create an url of the file " + absolutePath + "!");
            }

            ModuleClassLoader moduleClassLoader = new ModuleClassLoader(getParentClassLoader(), url);

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
