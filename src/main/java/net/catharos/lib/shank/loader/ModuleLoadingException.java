package net.catharos.lib.shank.loader;

import net.catharos.lib.shank.ModuleDescription;
import net.catharos.engine.core.lang.ArgumentException;
import org.jetbrains.annotations.Nullable;

/**
 * Thrown when a plugin failed to load
 */
public class ModuleLoadingException extends ArgumentException {

    private ModuleDescription module;

    public ModuleLoadingException(@Nullable ModuleDescription module, String message, Object... args) {
        super(message, args);
        this.module = module;
    }

    public ModuleLoadingException(@Nullable ModuleDescription module, Throwable cause, String message, Object... args) {
        super(cause, message, args);
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
