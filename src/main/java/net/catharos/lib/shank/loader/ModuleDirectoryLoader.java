package net.catharos.lib.shank.loader;

import net.catharos.engine.core.filesystem.Directory;
import net.catharos.lib.shank.ModuleDescription;
import net.catharos.lib.shank.loader.reflect.RegisteredModule;
import net.catharos.engine.core.util.NIOExtensionFilter;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a ModuleDirectoryLoader
 */
public abstract class ModuleDirectoryLoader extends ModuleLoader {
    private static final NIOExtensionFilter ARCHIVE_FILTER = new NIOExtensionFilter("jar", "zip", "plugin");
//    private final ObjectMapper mapper = new ObjectMapper();

    private final Logger logger;

    public ModuleDirectoryLoader(ClassLoader defaultParent, Directory modulesDirectory, Logger logger)  {
        super(defaultParent, modulesDirectory);
        this.logger = logger;
    }

    @Override
    public Set<ModuleDescription> loadDescriptions() throws ModuleLoadingException {
        Set<ModuleDescription> modules = new HashSet<>();

        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(getModulesDirectory().toPath(), ARCHIVE_FILTER);

            for (Path path : stream) {
                String absolutePath = path.toRealPath().toString();

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
                    url = path.toUri().toURL();
                } catch (MalformedURLException e) {
                    throw new ModuleLoadingException(null, "Could not create an url of the file " + absolutePath + "!");
                }

                ModuleClassLoader moduleClassLoader = new ModuleClassLoader(getParentClassLoader(), url);

                for (Class<?> moduleClass : getModuleClasses(url, moduleClassLoader)) {
                    RegisteredModule annotation = moduleClass.getAnnotation(RegisteredModule.class);
                    String name = annotation.name();

                    ModuleDescription<URL>  description = new ModuleDescription<>(name, moduleClass, url, moduleClassLoader);
                    modules.add(description);

                    moduleClassLoader.setModule(description);

                    logger.info("Loaded module %s!", name);
                }
            }

            stream.close();

        } catch (IOException e) {
            logger.catching(e);
        }

        return modules;
    }

    protected abstract Iterable<Class<?>> getModuleClasses(URL url, ModuleClassLoader classLoader);
}
