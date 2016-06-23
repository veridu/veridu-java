package com.veridu.exceptions;

/**
 * Class Empty Response
 */
public class EmptyResponse extends SDKException {

    public EmptyResponse() {
        super("Empty Response");
    }

    /**
     * Throws EmptyResponse Exception with message
     *
     * @param msg
     *            String
     */
    public EmptyResponse(String msg) {
        super(msg);
    }
}
