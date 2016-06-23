package com.veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.easymock.EasyMock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.veridu.endpoint.Request;
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
@PrepareForTest(Request.class)

public class RequestTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Request setUp() {
        Request request = EasyMock.createMockBuilder(Request.class).addMockedMethod("fetch", String.class, String.class)
                .addMockedMethod("fetch", String.class, String.class, String.class).createMock();
        request.storage = new Storage();
        request.storage.purgeSession();
        request.storage.setSessionToken("token");
        request.storage.setUsername("username");
        return request;
    }

    @Test
    public void testCreateReturnsBoolean()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, UnsupportedEncodingException {
        Request request = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(request.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(request);
        assertTrue(request.create("userFrom", "userTo", "type", "message"));
    }

    @Test(expected = EmptySession.class)
    public void testCreateThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        Request request = setUp();
        replay(request);
        request.storage.purgeSession();
        request.create("userFrom", "userTo", "type", "message");
    }

    @Test
    public void testListReturnsArray() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        Request request = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"list\":[\"json\"]}");
        expect(request.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(request);
        JSONArray array = new JSONArray();
        array.add("json");
        assertEquals(array, request.list("filter", 0, null));
    }

    @Test(expected = EmptySession.class)
    public void testListThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Request request = setUp();
        replay(request);
        request.storage.purgeSession();
        request.list("filter", 0, null);
    }

    @Test(expected = EmptyUsername.class)
    public void testListThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Request request = setUp();
        replay(request);
        request.storage.setUsername("");
        request.list("filter", 0, null);
    }

    @Test(expected = InvalidUsername.class)
    public void testListThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        Request request = setUp();
        replay(request);
        request.list("filter", 0, null, "@123#");
    }

    @Test
    public void testRetrieveReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        Request request = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"json\":\"response\"}");
        expect(request.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(request);
        assertEquals(json, request.retrieve());
    }

    @Test(expected = EmptySession.class)
    public void testRetrieveThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Request request = setUp();
        replay(request);
        request.storage.purgeSession();
        request.retrieve();
    }

    @Test(expected = EmptyUsername.class)
    public void testRetrieveThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Request request = setUp();
        replay(request);
        request.storage.setUsername("");
        request.retrieve();
    }

    @Test(expected = InvalidUsername.class)
    public void testRetrieveThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        Request request = setUp();
        replay(request);
        request.retrieve("@123#");
    }

}
