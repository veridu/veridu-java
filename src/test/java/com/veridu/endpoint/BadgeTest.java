package com.veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.easymock.EasyMock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.veridu.endpoint.Badge;
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
@PrepareForTest(Badge.class)
public class BadgeTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Badge setUp() {
        Badge badge = EasyMock.createMockBuilder(Badge.class).addMockedMethod("fetch", String.class, String.class)
                .addMockedMethod("fetch", String.class, String.class, String.class)
                .addMockedMethod("signedFetch", String.class, String.class, String.class).createMock();
        badge.storage = new Storage();
        badge.storage.purgeSession();
        badge.storage.setSessionToken("token");
        badge.storage.setUsername("username");
        return badge;
    }

    @Test
    public void testListAllReturnsArray() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        Badge badge = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"list\":[\"json\"]}");
        expect(badge.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(badge);
        JSONArray array = (JSONArray) parser.parse("[\"json\"]");
        assertEquals(array, badge.listAll());
    }

    @Test(expected = EmptySession.class)
    public void testListAllThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Badge badge = setUp();
        replay(badge);
        badge.storage.purgeSession();
        badge.listAll();
    }

    @Test(expected = EmptyUsername.class)
    public void testListAllThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Badge badge = setUp();
        replay(badge);
        badge.storage.setUsername("");
        badge.listAll();
    }

    @Test(expected = InvalidUsername.class)
    public void testListAllThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        Badge badge = setUp();
        replay(badge);
        badge.listAll("@123#");
    }

    @Test
    public void testRetrieveReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        Badge badge = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"json\":\"response\"}");
        expect(badge.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(badge);
        assertEquals(json, badge.retrieve(Badge.BADGE_CREDITCARD, Badge.TYPE_STATE));
    }

    @Test(expected = EmptySession.class)
    public void testRetrieveThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Badge badge = setUp();
        replay(badge);
        badge.storage.purgeSession();
        badge.retrieve(Badge.BADGE_CREDITCARD, Badge.TYPE_STATE);
    }

    @Test(expected = EmptyUsername.class)
    public void testRetrieveThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Badge badge = setUp();
        replay(badge);
        badge.storage.setUsername("");
        badge.retrieve(Badge.BADGE_CREDITCARD, Badge.TYPE_STATE);
    }

    @Test(expected = InvalidUsername.class)
    public void testRetrieveThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        Badge badge = setUp();
        replay(badge);
        badge.retrieve(Badge.BADGE_CREDITCARD, Badge.TYPE_STATE, "@123#");
    }

    @Test
    public void testCreateReturnsBoolean()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        Badge badge = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(badge.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(badge);
        assertTrue(badge.create(Badge.BADGE_TELECOM, new HashMap<String, String>()));

    }

    @Test(expected = EmptySession.class)
    public void testCreateThrowsEmptySession()
            throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed,
            SignatureFailed, NonceMismatch, InvalidUsername, UnsupportedEncodingException {
        Badge badge = setUp();
        replay(badge);
        badge.storage.purgeSession();
        badge.create(Badge.BADGE_TELECOM, new HashMap<String, String>());
    }

    @Test(expected = EmptyUsername.class)
    public void testCreateThrowsEmptyUsername()
            throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed,
            SignatureFailed, NonceMismatch, InvalidUsername, UnsupportedEncodingException {
        Badge badge = setUp();
        replay(badge);
        badge.storage.setUsername("");
        badge.create(Badge.BADGE_TELECOM, new HashMap<String, String>());
    }

    @Test(expected = InvalidUsername.class)
    public void testCreateThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, UnsupportedEncodingException {
        Badge badge = setUp();
        replay(badge);
        badge.create(Badge.BADGE_TELECOM, new HashMap<String, String>(), "@123#");
    }
}
