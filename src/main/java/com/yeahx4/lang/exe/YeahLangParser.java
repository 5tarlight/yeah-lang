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
public final class YeahLangParser {
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
//    public static void parse(String yeah, String path) throws InvalidYeahSyntaxException {
//        Stream<String> lineStream = yeah.lines();
//        List<String> lines = lineStream.toList();
//        Stack<ParseAction> actionStack = new Stack<>();
//
//        for (int i = 0; i < lines.size(); i++) {
//            String line = lines.get(i).trim();
//            if (line.isEmpty()) return;
//
//            boolean lineStartCheck = false;
//
//            for (String word : words) {
//                if (line.startsWith(word)) {
//                    lineStartCheck = true;
//                    // TODO do reserved word action
//                }
//            }
//
//            if (!lineStartCheck) {
//                for (String func : funcs) {
//                    if (line.startsWith(func)) {
//                        String f = "";
//
//                        switch (func) {
//                            case "print" -> {
//                                f = "print";
//                                if (line.length() < f.length()) {
//                                    throw new InvalidYeahSyntaxException(path, i, "Unexpected EOL");
//                                } else if (!line.contains("(")) {
//                                    throw new InvalidYeahSyntaxException(path, i, "Expected (");
//                                }
//
//                                String back = line.substring("print".length()).trim();
//                                if (!back.startsWith("("))
//                                    throw new InvalidYeahSyntaxException(path, i, "Expected (");
//
//                                actionStack.push(ParseAction.FUNCTION_EXE_PARAM_BRACE);
//                            }
//                        }
//                        // TODO reserved function action
//                    }
//                }
//            }
//        }
//    }
    public void parse(String yeah, String path) throws InvalidYeahSyntaxException {
        Stack<String> stack = new Stack<>();
        char[] chars = yeah.toCharArray();
        StringBuilder result = new StringBuilder();

        long id = 0L;
        int line = 1;

        for (int i = 0; i < chars.length; i++) {
            if (isInString(stack)) {
                // TODO string literal parsing
                continue;
            }

            char c = chars[i];

            if (c == '\n')
                line++;
            if (isEmpty(c))
                continue;

            if (c == '(') {
                String last = stack.lastElement();
                if (!Token.needSmallBrace(last)) {
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : '('");
                }
            } else if (c == ')') {
                continue;
            } else if (c == '}') {
                continue;
            } else if (c == '{') {
                continue;
            } else if (chars[i] == 'i') {
                // if
                if (checkReserved(chars, i, "if", true, path, line)) {
                    result.append(Token.IF_START).append(id).append("]");
                    id++;
                    i += 2;
                } else
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + c);
            } else if (chars[i] == 'f') {
                // for
                if (checkReserved(chars, i, "for", true, path, line)) {
                    result.append(Token.FOR_START).append(id).append("]");
                    id++;
                    i += 3;
                } else
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + c);
            } else if (chars[i] == 'w') {
                // while
                if (checkReserved(chars, i, "while", true, path, line)) {
                    result.append(Token.WHILE_START).append(id).append("]");
                    id++;
                    i += 5;
                } else
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + c);
            } else if (chars[i] == 'e') {
                // else, else if
                if (checkReserved(chars, i, "else if", true, path, line)) {
                    result.append(Token.ELSE_IF_START).append(id).append("]");
                    id++;
                    i += 7;
                } else if (checkReserved(chars, i, "else", false, path, line)) {
                    result.append(Token.ELSE_START).append(id).append("]");
                    id++;
                    i += 4;
                } else
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + c);
            } else {
                throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + c);
            }
        }

        System.out.println(result);
    }

    private boolean isInString(Stack<String> stack) {
        if (stack.isEmpty()) return false;
        return stack.lastElement().startsWith("[START_STR");
    }

    /**
     * Check is next string is reserved keywords and with brace if needed.
     *
     * @param chars Character array of input string
     * @param start start index of char array
     * @param token reserved keyword which will be checked
     * @param needSmallBrace does keyword require small brace ('(')
     * @param path path of read file. This will be displayed for @{link {@link InvalidYeahSyntaxException}}
     * @param line line of start char. This will be displayed for @{link {@link InvalidYeahSyntaxException}}
     * @return next char is reserved keyword
     * @throws InvalidYeahSyntaxException Unexpected syntax
     */
    private boolean checkReserved(
            char[] chars,
            int start,
            String token,
            boolean needSmallBrace,
            String path,
            int line
    ) throws InvalidYeahSyntaxException {
        boolean checked = false;
        boolean finished = false;

        if (!token.startsWith(String.valueOf(chars[start])))
            return false;
        else if (chars.length < start + token.length())
            if (!token.equals("else if"))
                throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + chars[start]);
            else
                return false;

        for (int i = start; !(i >= chars.length || finished); i++) {
            // Check reserved keyword
            if (i == start) {
                for (int j = 0; j < token.length(); j++) {
                    if (chars[start + j] != token.charAt(j)) {
                        if (!token.equals("else if"))
                            throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + chars[start + j]);
                    }
                }

                i += token.length() - 1;
                if (!needSmallBrace) {
                    checked = true;
                    finished = true;
                }
            } else {
//                System.out.println(i);
                if (isEmpty(chars[i]))
                    continue;

                if (chars[i] == '(') {
                    checked = true;
                    finished = true;
                } else {
                    // Not blank and not brace
                    throw new InvalidYeahSyntaxException(
                            path,
                            line,
                            "Unexpected token(expected '(') : " + chars[i]
                    );
                }
            }
        }

//        System.out.println(checked);
        return checked;
    }

    /**
     * check is given character is empty char.
     * Empty means blank or \n
     *
     * @param ch char input
     * @return is char empty
     */
    private boolean isEmpty(char ch) {
        return ch == ' ' || ch == '\n';
    }
}
