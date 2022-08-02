package com.yeahx4.cli.cmd;

public class Help extends CliExecutor {
    public Help() {
        super(str -> {
           System.out.println("=== Yeah Lang CLI help ===\n");

          CliExecutor.exe.keySet().forEach(key -> {
              System.out.printf("%s\t%s\n", key, CliExecutor.exe.get(key).desc);
          });
        }, "Usage of commands");
    }
}
