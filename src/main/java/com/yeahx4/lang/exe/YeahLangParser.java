package com.yeahx4.lang.exe;


import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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
    public void parse(String yeah, String path) throws InvalidYeahSyntaxException {
        Stack<String> stack = new Stack<>();
        char[] chars = yeah.toCharArray();
//        StringBuilder result = new StringBuilder();
        Deque<String> result = new LinkedList<>();

        long id = 0L;
        int line = 1;

        for (int i = 0; i < chars.length; i++) {
            if (isInString(stack)) {
                // TODO string literal parsing
                continue;
            }

            char c = chars[i];

            if (c == '\n') // TODO if \n is in string, ignore
                line++;
            if (isEmpty(c))
                continue;

            if (c == '(') {
                if (stack.size() < 1)
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : '('");

                String last = stack.lastElement();
                if (!Token.needSmallBrace(last)) {
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : '('");
                }

                if (last.equals(Token.IF) && result.getLast().startsWith(Token.IF_START)) {
                    int lastId = getLastStart(result, "IF", "", path, line, "(");
                    result.add(Token.IF_CON_START + lastId + "]");
                    stack.push(Token.IF_CON);
                } else if (last.equals(Token.ELSE_IF) && result.getLast().startsWith(Token.ELSE_IF_START)) {
                    int lastId = getLastStart(result, "ELSE_IF", "", path, line, "(");
                    result.add(Token.ELSE_IF_CON_START + lastId + "]");
                    stack.push(Token.ELSE_IF_CON);
                } else if (last.equals(Token.FOR) && result.getLast().startsWith(Token.FOR_START)) {
                    int lastId = getLastStart(result, "FOR", "", path, line, "(");
                    result.add(Token.FOR_CON_START + lastId + "]");
                    stack.push(Token.FOR_CON);
                } else if (last.equals(Token.WHILE) && result.getLast().startsWith(Token.WHILE_START)) {
                    int lastId = getLastStart(result, "WHILE", "", path, line, "(");
                    result.add(Token.WHILE_CON_START + lastId + "]");
                    stack.push(Token.WHILE_CON);
                }
            } else if (c == ')') {
                if (stack.size() < 1)
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : ')'");
                String last = stack.lastElement();

                if (last.contains("CON")) {
                    if (last.startsWith(Token.IF_CON)) {
                        int lastId = getLastStart(result, "IF", "CON", path, line, ")");
                        result.add(Token.IF_CON_END + lastId + "]");
                        stack.pop();
                    } else if (last.startsWith(Token.ELSE_IF_CON)) {
                        int lastId = getLastStart(result, "ELSE_IF", "CON", path, line, ")");
                        result.add(Token.ELSE_IF_CON_END + lastId + "]");
                        stack.pop();
                    } else if (last.startsWith(Token.FOR_CON)) {
                        int lastId = getLastStart(result, "FOR", "CON", path, line, ")");
                        result.add(Token.FOR_CON_END + lastId + "]");
                        stack.pop();
                    } else if (last.startsWith(Token.WHILE_CON)) {
                        int lastId = getLastStart(result, "WHILE", "CON", path, line, ")");
                        result.add(Token.WHILE_CON_END + lastId + "]");
                        stack.pop();
                    } else {
                        // TODO Inside condition brace
                    }
                }
            } else if (c == '{') {
                if (stack.size() < 1)
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : '{'");
                continue;
            } else if (c == '}') {
                if (stack.size() < 1)
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : '}'");
                continue;
            } else if (chars[i] == 'i') {
                // if
                if (checkReserved(chars, i, "if", true, path, line)) {
//                    result.append(Token.IF_START).append(id).append("]");
                    result.add(Token.IF_START + id + "]");
                    id++;
                    i += 2;
                    stack.push(Token.IF);
                } else
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + c);
            } else if (chars[i] == 'f') {
                // for
                if (checkReserved(chars, i, "for", true, path, line)) {
//                    result.append(Token.FOR_START).append(id).append("]");
                    result.add(Token.FOR_START + id + "]");
                    id++;
                    i += 3;
                    stack.push(Token.FOR);
                } else
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + c);
            } else if (chars[i] == 'w') {
                // while
                if (checkReserved(chars, i, "while", true, path, line)) {
//                    result.append(Token.WHILE_START).append(id).append("]");
                    result.add(Token.WHILE_START + id + "]");
                    id++;
                    i += 5;
                    stack.push(Token.WHILE);
                } else
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + c);
            } else if (chars[i] == 'e') {
                // else, else if
                if (checkReserved(chars, i, "else if", true, path, line)) {
//                    result.append(Token.ELSE_IF_START).append(id).append("]");
                    result.add(Token.ELSE_IF_START + id + "]");
                    id++;
                    i += 7;
                    stack.push(Token.ELSE_IF);
                } else if (checkReserved(chars, i, "else", false, path, line)) {
//                    result.append(Token.ELSE_START).append(id).append("]");
                    result.add(Token.ELSE_START + id + "]");
                    id++;
                    i += 4;
                    stack.push(Token.ELSE_BODY); // TODO Not body
                } else
                    throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + c);
            } else {
                throw new InvalidYeahSyntaxException(path, line, "Unexpected token : " + c);
            }
        }

        String[] array = new String[result.size()];
        result.toArray(array);
        String parsed = String.join("", array);

        System.out.println(parsed);
    }

    private int getLastStart(
            Deque<String> queue,
            String state,
            String type,
            String file,
            int line,
            String token
    ) throws InvalidYeahSyntaxException {
        if (queue.size() < 1)
            throw new InvalidYeahSyntaxException(file, line, String.format("Unexpected token '%s'", token));

        Stack<String> reverse = new Stack<>();
        for (String e : queue) {
            reverse.push(e);
        }

        boolean found = false;
        int id = -1;

        for (String e : reverse) {
            if (found)
                break;

            String query;
            if (type.equals(""))
                query = String.format("[START_%s_", state);
            else
                query = String.format("[START_%s_%s_", state, type);

            if (state.equals("ELSE") && type.equals("")) {
                if (e.startsWith(query) && !e.startsWith(query + "IF_")) {
                    found = true;
                    id = Integer.parseInt(e.substring(query.length(), e.length() - 1));
                }
            } else if (e.startsWith(query)) {
                found = true;
                id = Integer.parseInt(e.substring(query.length(), e.length() - 1));
            }
        }

        if (id == -1)
            throw new InvalidYeahSyntaxException(file, line, String.format("Unexpected token '%s'", token));

        return id;
    }

    @Deprecated
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
