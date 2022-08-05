package com.yeahx4.lang;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Read YEAH Lang file and interpret.
 * Work with {@link java.io.BufferedReader}
 *
 * @author yeahx4
 * @since 1.0
 */
public final class YeahLangReader {
    private final File file;

    public YeahLangReader(File file) {
        this.file = file;
    }

    public YeahLangReader(String path) {
        this.file = new File(path);
    }

    public YeahLangReader(File dir, String file) {
        this.file = new File(dir, file);
    }

    /**
     * Read String data from file.
     * Read data is raw String ({@code \n} and everything is included)
     *
     * @return read data
     * @throws IOException i/o exception occured
     */
    public String readFile() throws IOException {
        Path path = Path.of(this.file.getPath());
        StringBuilder builder = new StringBuilder();

        Stream<String> stream = Files.lines(Paths.get(path.toUri()), StandardCharsets.UTF_8);
        stream.forEach(s -> builder.append(s).append('\n'));
        stream.close();

        return builder.toString();
    }
}
