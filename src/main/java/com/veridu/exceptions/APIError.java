package com.veridu.exceptions;

/**
 * Class APIError
 */
public class APIError extends SDKException {

    public APIError() {
        super("API Error");
    }

    /**
     * throws API Error message
     *
     * @param msg
     *            String
     */
    public APIError(String msg) {
        super(msg);
    }
}
