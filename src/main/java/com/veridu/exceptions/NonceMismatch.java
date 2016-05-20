package com.veridu.exceptions;

/**
 * Class NonceMismnatch
 */
public class NonceMismatch extends SDKException {

    public NonceMismatch() {
        super("Nonce Mismatch");
    }

    /**
     * Throws NonceMismatch Exception with message
     *
     * @param msg
     *            String
     */
    public NonceMismatch(String msg) {
        super(msg);
    }
}
