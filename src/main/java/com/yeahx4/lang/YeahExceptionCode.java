package com.yeahx4.lang;

public enum YeahExceptionCode {
    INVALID_OPTION(1),
    INVALID_YEAH_FILE(2),
    INVALID_YEAH_SYNTAX(3);

    final private int code;

    private YeahExceptionCode(int code) {
        this.code = code;
    }

    public int toCode() {
        return this.code;
    }
}
