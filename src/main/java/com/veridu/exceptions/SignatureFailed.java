package com.veridu.exceptions;

/**
 * Class SignatureFailed 
 */
public class SignatureFailed extends Exception {

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
