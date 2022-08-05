package com.yeahx4.cli.cmd;

/**
 * CLI Argument parameters' function implementation.
 *
 * @author yeahx4
 * @since 1.0
 */
@FunctionalInterface
public interface CliFunction {
    /**
     * Some task executed when argument called.
     *
     * @param value argument's value. null if not defined.
     */
    void execute(String value);
}
