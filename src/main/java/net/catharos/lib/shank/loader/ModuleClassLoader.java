package net.catharos.lib.shank.loader;

import net.catharos.lib.shank.ModuleDescription;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Represents a ClassLoader for modules
 */
public class ModuleClassLoader extends URLClassLoader {
    private ModuleDescription module;

    public ModuleClassLoader(ClassLoader parent, URL... urls) {
        super(urls, parent);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    public void setModule(ModuleDescription module) {
        this.module = module;
    }

    public ModuleDescription getModule() {
        return module;
    }
}
