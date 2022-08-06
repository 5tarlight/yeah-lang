package com.yeahx4.lang;

/**
 * This exception means given cli argument contains file path (without dashes) but,
 * can't locate the given file.
 * This exception will be thrown after invoke of {@link java.net.URISyntaxException}
 *
 * @author yeahx4
 * @since 1.0
 * @see java.io.FileNotFoundException
 * @see java.net.URISyntaxException
 * @see YeahException
 */
public class InvalidYeahFileException extends YeahException {
    public InvalidYeahFileException(String input, String reason) {
        super(
                String.format("Invalid file %s : %s", input, reason),
                YeahExceptionCode.INVALID_YEAH_FILE
        );
    }
}
