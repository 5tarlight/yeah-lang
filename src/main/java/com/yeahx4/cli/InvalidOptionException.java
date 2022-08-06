package com.yeahx4.cli;

import com.yeahx4.lang.YeahException;
import com.yeahx4.lang.YeahExceptionCode;

/**
 * This exception will be thrown on start-up if arguments are weird.
 *
 * @author yeahx4
 * @since 1.0
 */
public class InvalidOptionException extends YeahException {
    public InvalidOptionException(String invalid) {
        super("Invalid Option : " + invalid, YeahExceptionCode.INVALID_OPTION);
    }
}
