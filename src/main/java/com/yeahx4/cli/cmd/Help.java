package com.yeahx4.cli.cmd;

/**
 * CLI Help Command.
 * Show all available arguments and description.
 *
 * @author yeahx4
 * @since 1.0
 */
public final class Help extends CliExecutor {
    /**
     * Print Help message.
     * List of commands will be automatically delivered from {@link CliExecutor}
     */
    public Help() {
        super(str -> {
           System.out.println("============== Yeah Lang CLI help ==============\n");

          CliExecutor.exe.keySet().forEach(key -> {
              System.out.printf("%s\t%s\n", key, CliExecutor.exe.get(key).desc);
          });
        }, "Usage of commands");
    }
}
