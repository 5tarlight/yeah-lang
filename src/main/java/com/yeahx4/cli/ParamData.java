package com.yeahx4.cli;

import java.util.HashMap;
import java.util.Map;

/**
 * CLI argument data class.
 *
 * @author yeahx4
 * @since 1.0
 */
public final class ParamData {
    public static Map<String, ParamType> params = new HashMap<>() {{
        put("H", ParamType.ONE_DASH_WITHOUT_VALUE);
        put("help", ParamType.TWO_DASH_WITHOUT_VALUE);
    }};

    /**
     * Major key of argument
     */
    public String key;
    /**
     * Value of key. null if not needed orr spam
     */
    public String content;
    public ParamType type;

    public ParamData(String key, String content, ParamType type) throws InvalidOptionException {
        this.key = key;
        this.content = content;
        this.type = type;

        if (!params.containsKey(key) || params.get(key) != type)
            throw new InvalidOptionException(key);
    }
}
