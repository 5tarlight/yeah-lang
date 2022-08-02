package com.yeahx4.cli;

public class InvalidOptionException extends Exception {
    public InvalidOptionException(String invalid) {
        super("Invalid Option : " + invalid);
    }
}
