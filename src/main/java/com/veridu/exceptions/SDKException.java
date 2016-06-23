package com.veridu.exceptions;

/**
 * Class SDK Exception
 */
public class SDKException extends Exception {

    public SDKException() {
        super("SDK Exception");
    }

    /**
     * Throws SDK Exception with message
     *
     * @param msg
     *            String
     */

    public SDKException(String message) {
        super(message);
    }
}
