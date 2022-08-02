package com.yeahx4.cli;

import java.util.ArrayList;
import java.util.List;

public class CliParamParser {
    public static List<ParamData> parseParam(String[] args) throws InvalidOptionException {
        int skipNext = 0;
        List<ParamData> params = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                String key = args[i].substring(2).trim();

                if (args.length > i + 1 && !args[i + 1].startsWith("-")) {
                    params.add(new ParamData(key, args[i + 1], ParamType.TWO_DASH_WITH_VALUE));
                    skipNext = 1;
                } else {
                    params.add(new ParamData(key, null, ParamType.TWO_DASH_WITHOUT_VALUE));
                }
            } else if (args[i].startsWith("-")) {
                String key = args[i].substring(1).trim();

                if (args.length > i + 1 && !args[i + 1].startsWith("-")) {
                    params.add(new ParamData(key, args[i + 1], ParamType.ONE_DASH_WITH_VALUE));
                    skipNext = 1;
                } else {
                    params.add(new ParamData(key, null, ParamType.ONE_DASH_WITHOUT_VALUE));
                }
            } else {
                params.add(new ParamData(args[i].trim(), null, ParamType.NO_DASH));
            }

            i += skipNext;
            skipNext = 0;
        }

        return params;
    }
}
