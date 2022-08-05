package com.yeahx4.cli.cmd;

import com.yeahx4.cli.InvalidOptionException;

import java.util.HashMap;
import java.util.Map;

public abstract class CliExecutor {
    public static final Map<String, CliExecutor> exe = new HashMap<>() {{
        put("help", new Help());
    }};

    public final CliFunction function;
    public final String desc;

    public CliExecutor(CliFunction function, String desc) {
        this.function = function;
        this.desc = desc;
    }

    public void execute(String value) {
        function.execute(value);
    }

    public static void exeKey(String key, String value) throws InvalidOptionException {
        String originalKey = null;

        switch (key) {
            case "help", "H" -> originalKey = "help";
        }

        if (!exe.containsKey(originalKey))
            throw new InvalidOptionException(key);

        exe.get(originalKey).execute(value);
    }
}
