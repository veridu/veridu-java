package com.veridu.exceptions;

/**
 * Class InvalidFormat
 */
public class InvalidFormat extends Exception {

    public InvalidFormat() {
        super("Invalid Response Format");
    }

    /**
     * Throws InvalidFormat Exception with message
     *
     * @param msg
     *            String
     */
    public InvalidFormat(String msg) {
        super(msg);
    }
}
