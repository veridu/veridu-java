package com.veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

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

import com.veridu.endpoint.Application;
import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.EmptyUsername;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.NonceMismatch;
import com.veridu.exceptions.RequestFailed;
import com.veridu.exceptions.SignatureFailed;
import com.veridu.signature.Signature;
import com.veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Application.class)
public class ApplicationTest {
    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Application setUp() {
        Application application = EasyMock.createMockBuilder(Application.class)
                .addMockedMethod("signedFetch", String.class, String.class)
                .addMockedMethod("signedFetch", String.class, String.class, String.class).createMock();
        application.storage = new Storage();
        application.storage.purgeSession();
        application.storage.setSessionToken("token");
        application.storage.setUsername("username");
        application.setKey("key");
        application.setSecret("secret");
        return application;
    }

    @Test
    public void testCreateReturnsInt()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, UnsupportedEncodingException {
        Application application = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"appid\":\"2\"}");
        expect(application.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(application);
        assertEquals(2, application.create("twitter"));
    }

    @Test(expected = EmptySession.class)
    public void testCreateThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, UnsupportedEncodingException {
        Application application = setUp();
        replay(application);
        application.storage.purgeSession();
        application.create("provider");
    }

    @Test
    public void testListAllReturnsArray() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername {
        Application application = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"list\":[\"json\"]}");
        expect(application.signedFetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(application);
        JSONArray array = (JSONArray) parser.parse("[\"json\"]");
        assertEquals(array, application.listAll());

    }

    @Test(expected = EmptySession.class)
    public void testListAllThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch {
        Application application = setUp();
        replay(application);
        application.storage.purgeSession();
        application.listAll();
    }

    @Test
    public void testDetailsReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername {
        Application application = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"list\":\"json\"}");
        expect(application.signedFetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(application);
        assertEquals(json, application.details(1));

    }

    @Test(expected = EmptySession.class)
    public void testDetailsThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch {
        Application application = setUp();
        replay(application);
        application.storage.purgeSession();
        application.details(1);
    }

    @Test
    public void testSetStateReturnsBoolean()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, UnsupportedEncodingException {
        Application application = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(application.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(application);
        assertEquals(true, application.setState(1, true));
    }

    @Test(expected = EmptySession.class)
    public void testSetStateThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, UnsupportedEncodingException {
        Application application = setUp();
        replay(application);
        application.storage.purgeSession();
        application.setState(1, true);
    }

}
