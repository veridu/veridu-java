package veridu.signature;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import org.junit.Test;

import veridu.signature.Signature;

public class SignatureTest {
    Signature signature = new Signature("clientid", "secret", "version");

    @Test
    public void testSignRequest() throws SignatureException, UnsupportedEncodingException {
        Object signedRequest = this.signature.signRequest("get", "resource", "nonce");
        assertTrue(signedRequest instanceof String);
    }

    @Test(expected = UnsupportedEncodingException.class)
    public void testSignRequestThrowsSignatureException() throws SignatureException, UnsupportedEncodingException {
        Signature signature = new Signature("clientid", "secret", "version".getBytes("").toString());
        signature.signRequest("get", "resource", "nonce");
    }
}
