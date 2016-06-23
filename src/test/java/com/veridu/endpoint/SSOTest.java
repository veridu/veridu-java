package com.veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.easymock.EasyMock;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.NonceMismatch;
import com.veridu.exceptions.RequestFailed;
import com.veridu.exceptions.SignatureFailed;
import com.veridu.signature.Signature;
import com.veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SSO.class)
public class SSOTest {
    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    @Test
    public void createOauth1ReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, UnsupportedEncodingException {
        SSO sso = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"json\":\"response\"}");
        expect(sso.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        expect(sso.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(sso);
        assertEquals(json, sso.createOauth1("provider", new HashMap<String, String>()));
    }

    @Test(expected = EmptySession.class)
    public void createOauth1ThrowsEmptySession() throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException, ParseException {
        SSO sso = setUp();
        replay(sso);
        sso.storage.purgeSession();
        sso.createOauth1("provider", new HashMap<String, String>());
    }

    @Test
    public void createOauth2ReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, UnsupportedEncodingException {
        SSO sso = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"json\":\"response\"}");
        expect(sso.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(sso);
        assertEquals(json, sso.createOauth1("provider", new HashMap<String, String>()));
    }

    @Test(expected = EmptySession.class)
    public void createOauth2ThrowsEmptySession() throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException, ParseException {
        SSO sso = setUp();
        replay(sso);
        sso.storage.purgeSession();
        sso.createOauth2("provider", new HashMap<String, String>());
    }

    public SSO setUp() {
        SSO sso = EasyMock.createMockBuilder(SSO.class)
                .addMockedMethod("signedFetch", String.class, String.class, String.class).createMock();
        sso.storage = new Storage();
        sso.storage.purgeSession();
        sso.storage.setSessionToken("token");
        sso.storage.setUsername("username");
        return sso;
    }

}
