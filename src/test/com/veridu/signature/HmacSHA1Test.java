package veridu.signature;

import static org.junit.Assert.assertTrue;

import java.security.SignatureException;

import org.junit.Test;

import veridu.signature.HmacSHA1;

public class HmacSHA1Test {

    @Test
    public void testCalculate() throws SignatureException {
        String data = "data";
        String key = "key";
        Object value = HmacSHA1.Calculate(data, key);
        assertTrue(value instanceof String);
    }

    @Test(expected = SignatureException.class)
    public void testCalculateThrowsSignatureException() throws SignatureException {
        String data = "";
        String key = "";
        HmacSHA1.Calculate(data, key);
    }
}
