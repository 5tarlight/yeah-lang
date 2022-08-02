package com.yeahx4.cli.cmd;

@FunctionalInterface
public interface CliFunction {
    void execute(String value);
}
