package com.yeahx4.lang;

import java.util.Arrays;
import java.util.List;

/**
 * Yeah lang exception.
 * Exception code {@link YeahExceptionCode} is assigned.
 * {@link #hashCode()} will return exception code
 *
 * @author yeahx4
 * @since 1.0
 * @see java.lang.Exception
 * @see YeahExceptionCode
 */
public class YeahException extends Exception {
    public YeahExceptionCode code;

    public YeahException(YeahExceptionCode code) {
        this.code = code;
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public YeahException(String message, YeahExceptionCode code) {
        super(message);
        this.code = code;
    }

    /**
     * get exception code
     *
     * @return exception code
     */
    @Override
    public int hashCode() {
        return this.code.toCode();
    }

    /**
     * Returns a short description of this throwable.
     * The result is the concatenation of:
     * <ul>
     * <li> the {@linkplain Class#getName() name} of the class of this object
     * <li> ": " (a colon and a space)
     * <li> the result of invoking this object's {@link #getLocalizedMessage}
     *      method
     * </ul>
     * If {@code getLocalizedMessage} returns {@code null}, then just
     * the class name is returned.
     *
     * @return a string representation of this throwable.
     */
    @Override
    public String toString() {
        String str = super.toString();
        return String.format("yeah exception %s code : %d\n", code.toString(), code.toCode()) + str;
    }
}
