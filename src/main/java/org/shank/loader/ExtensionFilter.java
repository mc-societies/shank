package org.shank.loader;


import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Uses old io to filter a directory by a specific extension/s
 */
public class ExtensionFilter implements FilenameFilter {
    private final String[] extensions;

    public ExtensionFilter(String... extension) {
        this.extensions = extension;
    }

    @Override
    public boolean accept(File dir, String name) {
        return FilenameUtils.isExtension(name, extensions);
    }
}
