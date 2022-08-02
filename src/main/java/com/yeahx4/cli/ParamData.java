package com.yeahx4.cli;

import java.util.HashMap;
import java.util.Map;

public class ParamData {
    public static Map<String, ParamType> params = new HashMap<>() {{
        put("H", ParamType.ONE_DASH_WITHOUT_VALUE);
        put("help", ParamType.TWO_DASH_WITHOUT_VALUE);
    }};

    public String key;
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
