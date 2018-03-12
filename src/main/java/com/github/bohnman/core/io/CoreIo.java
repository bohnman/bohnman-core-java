package com.github.bohnman.core.io;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CoreIo {

    public static String toString(String path, Charset charset) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            return new String(bytes, charset);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String toString(File file, Charset charset) {
        return toString(file.getAbsolutePath(), charset);
    }

}
