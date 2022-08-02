package com.yeahx4;

import com.yeahx4.cli.CliParamParser;
import com.yeahx4.cli.InvalidOptionException;
import com.yeahx4.cli.ParamData;
import com.yeahx4.cli.cmd.CliExecutor;

import java.util.List;

public class Main {
    private static void printHelp() {
        System.out.println("Yeah Lang help page");
    }

    public static void main(String[] args) throws InvalidOptionException {
        if (args.length == 0) printHelp();

        List<ParamData> params = CliParamParser.parseParam(args);

        for (var p : params) {
            CliExecutor.exeKey(p.key, p.content);
        }
    }
}
