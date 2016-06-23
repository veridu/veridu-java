package com.veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

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
import com.veridu.exceptions.UnavailableRegion;
import com.veridu.signature.Signature;
import com.veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Password.class)
public class PasswordTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Password setUp() {
        Password password = EasyMock.createMockBuilder(Password.class)
                .addMockedMethod("signedFetch", String.class, String.class, String.class).createMock();
        password.storage = new Storage();
        password.storage.purgeSession();
        password.storage.setSessionToken("token");
        password.storage.setUsername("username");
        return password;
    }

    @Test
    public void testCreateReturnsJson()
            throws SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, ParseException, EmptySession, UnavailableRegion, UnsupportedEncodingException {
        Password password = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"json\":\"response\"}");
        expect(password.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(password);
        assertEquals(json, password.create("firstname", "lastname", "email", "password"));
    }

    @Test(expected = EmptySession.class)
    public void testCreateThrowsEmptySession()
            throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnavailableRegion, UnsupportedEncodingException, ParseException {
        Password password = setUp();
        replay(password);
        password.storage.purgeSession();
        password.create("firstname", "lastname", "email", "password");
    }

    @Test
    public void testLoginReturnsString()
            throws SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, ParseException, EmptySession, UnavailableRegion, UnsupportedEncodingException {
        Password password = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"veridu_id\":\"id\"}");
        expect(password.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(password);
        assertEquals("id", password.login("email", "password"));
    }

    @Test(expected = EmptySession.class)
    public void testLoginThrowsEmptySession()
            throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnavailableRegion, UnsupportedEncodingException, ParseException {
        Password password = setUp();
        replay(password);
        password.storage.purgeSession();
        password.login("email", "password");
    }

    @Test
    public void testRecoverFirstStepReturnsTrue()
            throws SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, ParseException, EmptySession, UnavailableRegion, UnsupportedEncodingException {
        Password password = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(password.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(password);
        assertTrue(password.recoverFirstStep("email", "setupUrl"));
    }

    @Test(expected = EmptySession.class)
    public void testRecoverFirstStepThrowsEmptySession()
            throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnavailableRegion, UnsupportedEncodingException, ParseException {
        Password password = setUp();
        replay(password);
        password.storage.purgeSession();
        password.recoverFirstStep("email", "setupUrl");
    }

    @Test
    public void testRecoverSecondStepReturnsTrue()
            throws SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, ParseException, EmptySession, UnavailableRegion, UnsupportedEncodingException {
        Password password = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(password.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(password);
        assertTrue(password.recoverSecondStep("recover_hash", "password"));
    }

    @Test(expected = EmptySession.class)
    public void testRecoverSecondStepThrowsEmptySession()
            throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnavailableRegion, UnsupportedEncodingException, ParseException {
        Password password = setUp();
        replay(password);
        password.storage.purgeSession();
        password.recoverSecondStep("recover_hash", "password");
    }
}
