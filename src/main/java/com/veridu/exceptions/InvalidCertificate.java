package com.veridu.exceptions;

/**
 * Class InvalidCertificate
 */
public class InvalidCertificate extends SDKException {

    public InvalidCertificate() {
        super("Invalid Certificate");
    }

    /**
     * Throws InvalidCertificate Exception with message
     *
     * @param msg
     *            String
     */
    public InvalidCertificate(String msg) {
        super(msg);
    }
}
