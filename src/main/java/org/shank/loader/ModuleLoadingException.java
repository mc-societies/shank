package org.shank.loader;

import org.jetbrains.annotations.Nullable;
import org.shank.ModuleDescription;

/**
 * Thrown when a plugin failed to load
 */
public class ModuleLoadingException extends Exception {

    private ModuleDescription module;

    public ModuleLoadingException(@Nullable ModuleDescription module, String message) {
        super(message);
        this.module = module;
    }

    public ModuleLoadingException(@Nullable ModuleDescription module, Throwable cause, String message) {
        super(message, cause);
        this.module = module;
    }

    public ModuleLoadingException(@Nullable ModuleDescription module, Throwable cause) {
        super(cause);
        this.module = module;
    }

    @Nullable
    public ModuleDescription getModule() {
        return module;
    }
}