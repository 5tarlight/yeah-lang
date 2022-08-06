package com.yeahx4.cli.cmd;

import com.yeahx4.cli.InvalidOptionException;
import com.yeahx4.lang.InvalidYeahFileException;

import java.util.HashMap;
import java.util.Map;

/**
 * CLI Argument executable super class.
 */
public abstract class CliExecutor {
    /**
     * Map of cli arguments
     */
    public static final Map<String, CliExecutor> exe = new HashMap<>() {{
        put("help", new Help());
    }};

    /**
     * Executable body
     */
    public final CliFunction function;
    /**
     * Help message
     */
    public final String desc;

    public CliExecutor(CliFunction function, String desc) {
        this.function = function;
        this.desc = desc;
    }

    /**
     * run executable
     *
     * @param value cli argument parameter. {@code null} if not defined
     */
    public void execute(String value) {
        function.execute(value);
    }

    /**
     * execute {@link CliExecutor} sub classes.
     *
     * @param key argument key. if not valid dash numbers,
     * @param value argument parameter. {@link null} if not defined
     * @throws InvalidOptionException if key is not valid
     * @throws InvalidYeahFileException if given file path is not valid
     */
    public static void exeKey(String key, String value)
            throws InvalidOptionException, InvalidYeahFileException {
        String originalKey = null;

        switch (key) {
            case "help", "H" -> originalKey = "help";
        }

        if (key.endsWith(".yeah"))
            FileExecutor.runFile(key);
        else if (!exe.containsKey(originalKey))
            throw new InvalidOptionException(key);
        else
            exe.get(originalKey).execute(value);
    }
}
