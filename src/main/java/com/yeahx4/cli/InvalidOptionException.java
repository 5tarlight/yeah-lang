package com.yeahx4.cli;

/**
 * This exception will be thrown on start-up if arguments are weird.
 *
 * @author yeahx4
 * @since 1.0
 */
public class InvalidOptionException extends Exception {
    public InvalidOptionException(String invalid) {
        super("Invalid Option : " + invalid);
    }
}
