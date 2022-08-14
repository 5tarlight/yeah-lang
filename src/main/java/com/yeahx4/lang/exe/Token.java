package com.yeahx4.lang.exe;

import java.util.Stack;

/**
 * language parsing tokenizer.
 * Struct code structure
 *
 * @author yeahx4
 * @since 1.0
 */
public final class Token<T> {
    private final static String LIT_PRE = "[LIT_";
    private final static String STR_PRE = "STR_";
    private final static String INT_PRE = "INT_";
    private final static String DBL_PRE = "DBL_";
    private final static String BOL_PRE = "BOL_";

    public final static String IF_START = "[START_IF_";
    public final static String ELSE_IF_START = "[START_ELSE_IF_";
    public final static String ELSE_START = "[START_ELSE";
    public final static String FOR_START = "[START_FOR_";
    public final static String WHILE_START = "[START_FOR_";

    public final static String IF_CON = "IF_CON";
    public final static String IF_CON_START = "[START_IF_CON_";
    public final static String IF_CON_END = "[END_IF_CON_";
    public final static String ELSE_IF_CON = "ELSE_IF_CON";
    public final static String ELSE_IF_CON_START = "[START_ELSE_IF_CON_";
    public final static String ELSE_IF_CON_END = "[END_ELSE_IF_CON_";
    public final static String FOR_CON = "FOR_CON";
    public final static String FOR_CON_START = "[START_FOR_CON_";
    public final static String FOR_CON_END = "[END_FOR_CON_";
    public final static String WHILE_CON = "WHILE_CON";
    public final static String WHILE_CON_START = "[START_WHILE_CON_";
    public final static String WHILE_CON_END = "[END_WHILE_CON_";

    public final static String IF_BODY = "IF_BODY";
    public final static String IF_BODY_START = "[START_IF_BODY_";
    public final static String IF_BODY_END = "[END_IF_BODY_";
    public final static String ELSE_IF_BODY = "ELSE_IF_BODY";
    public final static String ELSE_IF_BODY_START = "[START_ELSE_IF_BODY_";
    public final static String ELSE_IF_BODY_END = "[END_ELSE_IF_BODY_";
    public final static String ELSE_BODY = "ELSE_BODY";
    public final static String ELSE_BODY_START = "[START_ELSE_BODY_";
    public final static String ELSE_BODY_END = "[END_ELSE_BODY_";
    public final static String FOR_BODY = "FOR_BODY";
    public final static String FOR_BODY_START = "[START_FOR_BODY_";
    public final static String FOR_BODY_END = "[FOR_BODY_END_";
    public final static String WHILE_BODY = "WHILE_BODY";
    public final static String WHILE_BODY_START = "[START_WHILE_BODY_";
    public final static String WHILE_BODY_END = "[END_WHILE_BODY_";

    private final static String FUNC_PRINT = "[F_P]";

    public static boolean needSmallBrace(String last) {
        return last.contains("CON");
    }

    private static int getLitPreLength(String pre) {
        return LIT_PRE.length() + pre.length();
    }

    private static int getLitPreLength() {
        return getLitPreLength(STR_PRE);
    }

    /**
     * Encrypt raw string data to token.
     * Encrypted token is [LIT_STR_${literal}]
     *
     * @param data raw String data
     * @return encrypted token
     */
    public static String encryptString(String data) {
        return LIT_PRE + STR_PRE + data + "]";
    }

    /**
     * Encrypt raw int data to token.
     * Encrypted token is [LIT_INT_${literal}]
     *
     * @param data raw int data
     * @return encrypted token
     */
    public static String encryptInt(int data) {
        return LIT_PRE + INT_PRE + data + "]";
    }

    /**
     * Encrypt raw double data to token.
     * Encrypted token is [LIT_DBL_${literal}]
     *
     * @param data raw double data
     * @return encrypted token
     */
    public static String encryptDouble(double data) {
        return LIT_PRE + DBL_PRE + data + "]";
    }

    /**
     * Encrypt raw boolean data to token.
     * Encrypted token is [LIT_BOL_${literal}]
     *
     * @param data raw boolean data
     * @return encrypted token
     */
    public static String encryptBoolean(boolean data) {
        return LIT_PRE + BOL_PRE + data + "]";
    }

    /**
     * Decrypt parsed data by {@link #encryptString(String)}
     *
     * @param encrypt parsed token
     * @return raw string data
     */
    public static String decryptString(String encrypt) {
        return encrypt.substring(getLitPreLength(), encrypt.length() - 1);
    }

    /**
     * Decrypt parsed data by {@link #encryptInt(int)}
     *
     * @param encrypt parsed token
     * @return raw int data
     */
    public static int decryptInt(String encrypt) {
        return Integer.parseInt(encrypt.substring(getLitPreLength(), encrypt.length() - 1));
    }

    /**
     * Decrypt parsed data by {@link #encryptDouble(double)}
     *
     * @param encrypt parsed token
     * @return raw double data
     */
    public static double decryptDouble(String encrypt) {
        return Double.parseDouble(encrypt.substring(getLitPreLength(), encrypt.length() - 1));
    }

    /**
     * Decrypt parsed data by {@link #encryptBoolean(boolean)}
     *
     * @param encrypt parsed token
     * @return raw boolean data
     */
    public static boolean decryptBoolean(String encrypt) {
        return Boolean.parseBoolean(encrypt.substring(getLitPreLength(), encrypt.length() - 1));
    }

    /**
     * Encrypt token
     */
    public String token;
    /**
     * Type class of raw token value
     */
    public Class<?> type;

    /**
     * Create token instance with data.
     *
     * @param data raw data
     * @deprecated do not directly create token instance.
     *             encrypt&decrypt through static methods
     */
    @Deprecated
    public Token(T data) {
        type = data.getClass();

        if (type == String.class)
            token = encryptString((String)data);
        else if (type == Integer.class)
            token = encryptInt((int)data);
        else if (type == Double.class)
            token = encryptDouble((double)data);
        else if (type == Boolean.class)
            token = encryptBoolean((boolean)data);
        else
            token = encryptString("OMG This must not happen");
    }

    /**
     * Return original value with boxed literal parsing.
     * This use unchecked casting and may cause exception.
     *
     * @return original Literal
     * @deprecated use static decrypt methods
     */
    @Deprecated
    public T getValue() {
        if (type == String.class)
            return (T)decryptString(token);
        else if (type == Integer.class)
            return (T)Integer.valueOf(decryptInt(token));
        else if (type == Double.class)
            return (T)Double.valueOf(decryptDouble(token));
        else if (type == Boolean.class)
            return (T)Boolean.valueOf(decryptBoolean(token));
        else
            return null;
    }

    @Override
    public String toString() {
        return token;
    }
}
