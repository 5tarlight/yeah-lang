package com.yeahx4;

import com.yeahx4.cli.CliParamParser;
import com.yeahx4.cli.InvalidOptionException;
import com.yeahx4.cli.ParamData;
import com.yeahx4.cli.cmd.CliExecutor;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InvalidOptionException {
        if (args.length == 0)
            CliExecutor.exe.get("help").function.execute(null);

        List<ParamData> params = CliParamParser.parseParam(args);

        for (var p : params) {
            CliExecutor.exeKey(p.key, p.content);
        }
    }
}
