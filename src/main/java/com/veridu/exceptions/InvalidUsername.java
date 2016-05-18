package com.veridu.exceptions;

/**
 * Class InvalidUsername
 */
public class InvalidUsername extends Exception {

    public InvalidUsername() {
        super("Invalid Username given");
    }

    /**
     * Throws InvalidUsername Exception with message
     *
     * @param msg
     *            String
     */
    public InvalidUsername(String msg) {
        super(msg);
    }
}
