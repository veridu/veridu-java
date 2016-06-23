package com.veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.easymock.EasyMock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.veridu.endpoint.AbstractEndpoint;
import com.veridu.endpoint.Provider;
import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.EmptyUsername;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.InvalidUsername;
import com.veridu.exceptions.RequestFailed;
import com.veridu.signature.Signature;
import com.veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Provider.class)

public class ProviderTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();
    
    public Provider setUp() {
        Provider provider = EasyMock.createMockBuilder(Provider.class)
                .addMockedMethod("fetch", String.class, String.class)
                .addMockedMethod("fetch", String.class, String.class, String.class).createMock();
        provider.storage = new Storage();
        provider.storage.purgeSession();
        provider.storage.setSessionToken("token");
        provider.storage.setUsername("username");
        return provider;
    }

    @Test
    public void testCheckReturnsBoolean() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        Provider provider = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"state\":\"true\"}");
        expect(provider.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(provider);
        assertTrue(provider.check("user"));
    }

    @Test(expected = EmptyUsername.class)
    public void testCheckThrowEmtpyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Provider provider = setUp();
        replay(provider);
        provider.storage.setUsername("");
        provider.check("provider");
    }

    @Test(expected = EmptySession.class)
    public void testCheckThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Provider provider = setUp();
        replay(provider);
        provider.storage.purgeSession();
        provider.check("provider", "user");
    }

    @Test(expected = InvalidUsername.class)
    public void testCheckThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        Provider provider = setUp();
        replay(provider);
        provider.check("provider", "user");
    }

    @Test
    public void testCreateOauth1ReturnsTaskId() throws EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, EmptySession, EmptyUsername, ParseException, InvalidUsername, UnsupportedEncodingException {
        Provider provider = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"task_id\":\"1\"}");
        expect(provider.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(provider);
        assertEquals("1", provider.createOAuth1("provider", new HashMap<String, String>()));
    }

    @Test(expected = EmptySession.class)
    public void testCreateOauth1ThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        Provider provider = setUp();
        provider.storage.purgeSession();
        replay(provider);
        provider.createOAuth1("provider", new HashMap<String, String>());
    }

    @Test(expected = EmptyUsername.class)
    public void testCreateOauth1ThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        Provider provider = setUp();
        provider.storage.setUsername("");
        replay(provider);
        provider.createOAuth1("provider", new HashMap<String, String>());
    }

    @Test
    public void testCreateOauth2ReturnsTaskId() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        Provider provider = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"task_id\":\"1\"}");
        expect(provider.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(provider);
        assertEquals("1", provider.createOAuth2("provider", new HashMap<String, String>()));
    }

    @Test(expected = EmptySession.class)
    public void testCreateOauth2ThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        Provider provider = setUp();
        provider.storage.purgeSession();
        replay(provider);
        provider.createOAuth2("provider", new HashMap<String, String>());
    }

    @Test(expected = EmptyUsername.class)
    public void testCreateOauth2ThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        Provider provider = setUp();
        provider.storage.setUsername("");
        replay(provider);
        provider.createOAuth2("provider", new HashMap<String, String>());
    }

    @Test
    public void testGetAllDetailsReturnsJson() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        Provider provider = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"task_id\":\"1\"}");
        expect(provider.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(provider);
        assertEquals(json, provider.getAllDetails("user"));
    }

    @Test(expected = EmptyUsername.class)
    public void testGetAllDetailsThrowEmtpyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Provider provider = setUp();
        provider.storage.setUsername("");
        replay(provider);
        provider.getAllDetails();
    }

    @Test(expected = EmptySession.class)
    public void testGetAllDetailsThrowsEmptySession() throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        Provider provider = setUp();
        provider.storage.purgeSession();
        replay(provider);
        provider.getAllDetails("user");
    }

    @Test(expected = InvalidUsername.class)
    public void testGetAllDetailsThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        Provider provider = setUp();
        replay(provider);
        provider.getAllDetails("@123#");
    }

    @Test
    public void testListAllReturnsArray() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        Provider provider = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"list\":[\"response\"]}");
        expect(provider.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(provider);
        JSONArray array = new JSONArray();
        array.add("response");
        assertEquals(array, provider.listAll("user"));
    }

    @Test(expected = EmptyUsername.class)
    public void testListAllThrowEmtpyUsername() throws EmptyUsername, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptySession {
        Provider provider = setUp();
        replay(provider);
        provider.storage.setUsername("");
        provider.listAll();
    }

    @Test(expected = EmptySession.class)
    public void testListAllThrowsEmptySession() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        Provider provider = setUp();
        replay(provider);
        provider.storage.purgeSession();
        provider.listAll("user");
    }

    @Test(expected = InvalidUsername.class)
    public void testListAllThrowsInvalidUsername() throws EmptyUsername, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptySession {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        Provider provider = setUp();
        replay(provider);
        provider.listAll("@123#");
    }

}