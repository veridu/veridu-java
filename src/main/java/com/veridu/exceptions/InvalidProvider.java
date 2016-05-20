package com.veridu.exceptions;

/**
 * Class InvalidProvider
 */
public class InvalidProvider extends SDKException {
    public InvalidProvider() {
        super("Invalid Response Provider");
    }

    /**
     * Throws InvalidProvider Exception with message
     *
     * @param msg
     *            String
     */
    public InvalidProvider(String msg) {
        super(msg);
    }
}
