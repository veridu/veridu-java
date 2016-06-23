package com.veridu.exceptions;

/**
 * Class SignatureFailed
 */
public class SignatureFailed extends SDKException {

    public SignatureFailed() {
        super("Signature Failed");
    }
    /**
     * Throws SignatureFailed Exception with message
     *
     * @param msg String
     */
    public SignatureFailed(String msg) {
        super(msg);
    }
}
