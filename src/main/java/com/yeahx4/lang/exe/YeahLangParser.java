package com.yeahx4.lang.exe;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * Interpret yeah lang file.
 * You can also parse script.
 *
 * @author yeahx4
 * @since 1.0
 */
public class YeahLangParser {
    private static List<String> words = Arrays.asList(
            "if", "else if", "else",
            "for", "while", "do"
    );
    private static List<String> funcs = Arrays.asList(
            "print", "println", "printf"
    );

    /**
     * parse and interpret yeah lang content.
     *
     * @param yeah content of yeah lang file or inline script.
     * @param path parsing file's absolute path
     * @throws InvalidYeahSyntaxException content of file is not valid yeah lang syntax
     */
    public static void parse(String yeah, String path) throws InvalidYeahSyntaxException {
        Stream<String> lineStream = yeah.lines();
        List<String> lines = lineStream.toList();
        Stack<ParseAction> actionStack = new Stack<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) return;

            boolean lineStartCheck = false;

            for (String word : words) {
                if (line.startsWith(word)) {
                    lineStartCheck = true;
                    // TODO do reserved word action
                }
            }

            if (!lineStartCheck) {
                for (String func : funcs) {
                    if (line.startsWith(func)) {
                        String f = "";

                        switch (func) {
                            case "print" -> {
                                f = "print";
                                if (line.length() < f.length()) {
                                    throw new InvalidYeahSyntaxException(path, i, "Unexpected EOL");
                                } else if (!line.contains("(")) {
                                    throw new InvalidYeahSyntaxException(path, i, "Expected (");
                                }

                                String back = line.substring("print".length()).trim();
                                if (!back.startsWith("("))
                                    throw new InvalidYeahSyntaxException(path, i, "Expected (");

                                actionStack.push(ParseAction.FUNCTION_EXE_PARAM_BRACE);
                            }
                        }
                        // TODO reserved function action
                    }
                }
            }
        }
    }
}
