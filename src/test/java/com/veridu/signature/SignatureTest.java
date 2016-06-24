package com.veridu.signature;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import org.json.simple.parser.ParseException;
import org.junit.Test;

public class SignatureTest {
    Signature signature = new Signature("clientid", "secret", "0.3");

    @Test
    public void testSignRequest() throws SignatureException, UnsupportedEncodingException, ParseException {
        Object signedRequest = this.signature.signRequest("get", "resource", "nonce");
        assertTrue(signedRequest instanceof String);
    }

    @Test(expected = UnsupportedEncodingException.class)
    public void testSignRequestThrowsSignatureException()
            throws SignatureException, UnsupportedEncodingException, ParseException {
        Signature signature = new Signature("clientid", "secret", "version".getBytes("").toString());
        signature.signRequest("get", "resource", "nonce");
    }
}
