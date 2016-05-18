package com.veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.easymock.EasyMock;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.veridu.endpoint.Raw;
import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.EmptyUsername;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.InvalidUsername;
import com.veridu.exceptions.NonceMismatch;
import com.veridu.exceptions.RequestFailed;
import com.veridu.exceptions.SignatureFailed;
import com.veridu.signature.Signature;
import com.veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Raw.class)
public class RawTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Raw setUp() {
        Raw raw = EasyMock.createMockBuilder(Raw.class).addMockedMethod("fetch", String.class, String.class)
                .addMockedMethod("fetch", String.class, String.class, String.class)
                .addMockedMethod("signedFetch", String.class, String.class, String.class).createMock();
        raw.storage = new Storage();
        raw.storage.purgeSession();
        raw.storage.setSessionToken("token");
        raw.storage.setUsername("username");
        return raw;
    }

    @Test
    public void testRetrieveReturnsJson()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        Raw raw = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"credential\":{\"credentials\":\"Cre123\"}}");
        expect(raw.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(raw);
        JSONObject obj = (JSONObject) parser.parse("{\"credentials\":\"Cre123\"}");
        assertEquals(obj, raw.retrieve(Raw.TYPE_CREDENTIAL));
    }

    @Test(expected = EmptySession.class)
    public void testRetrieveThrowsEmptySession()
            throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed,
            SignatureFailed, NonceMismatch, InvalidUsername, UnsupportedEncodingException {
        Raw raw = setUp();
        replay(raw);
        raw.storage.purgeSession();
        raw.retrieve(Raw.TYPE_CREDENTIAL);
    }

    @Test(expected = EmptyUsername.class)
    public void testRetrieveThrowsEmptyUsername()
            throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed,
            SignatureFailed, NonceMismatch, InvalidUsername, UnsupportedEncodingException {
        Raw raw = setUp();
        replay(raw);
        raw.storage.setUsername("");
        raw.retrieve(Raw.TYPE_CREDENTIAL);
    }

    @Test(expected = InvalidUsername.class)
    public void testRetrieveThrowsInvalidUsername()
            throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, SignatureFailed, NonceMismatch, EmptyUsername, UnsupportedEncodingException {
        Raw raw = setUp();
        replay(raw);
        raw.retrieve(Raw.TYPE_CREDENTIAL, "@123#");
    }

}
