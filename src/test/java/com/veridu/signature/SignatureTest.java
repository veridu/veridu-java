package com.veridu.signature;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import org.easymock.EasyMock;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.veridu.Utils;

public class SignatureTest {
    Signature signature = new Signature("clientid", "secret", "0.3");

    @Test
    public void testSignRequest() throws SignatureException, UnsupportedEncodingException, ParseException {
        Utils utils = EasyMock.createMockBuilder(Utils.class).addMockedMethod("readConfig", String.class).createMock();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"BASEURL\":\"http://api.veridu.com\"}");
        expect(utils.readConfig(isA(String.class))).andReturn(json);
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
