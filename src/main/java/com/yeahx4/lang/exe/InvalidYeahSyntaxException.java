package com.yeahx4.lang.exe;

import com.yeahx4.lang.YeahException;
import com.yeahx4.lang.YeahExceptionCode;

/**
 * This exception means some yeah file reading is seemed to have wrong syntax in its content.
 *
 * @author yeahx4
 * @since 1.0
 */
public class InvalidYeahSyntaxException extends YeahException {
    /**
     * Create new {@link InvalidYeahSyntaxException} instance
     *
     * @param file path of file with wrong syntax
     * @param line line of file content with wrong syntax
     * @param reason reason why this exception thrown
     */
    public InvalidYeahSyntaxException(String file, int line, String reason) {
        super(
                String.format("Invalid syntax in %s(line %d) : %s", file, line, reason),
                YeahExceptionCode.INVALID_YEAH_SYNTAX
        );
    }
}
