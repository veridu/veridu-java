package com.veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;

import java.io.UnsupportedEncodingException;

import org.easymock.EasyMock;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.WhiteboxImpl;

import com.veridu.endpoint.AbstractEndpoint;
import com.veridu.endpoint.Check;
import com.veridu.endpoint.Profile;
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
@PrepareForTest(Profile.class)

public class ProfileTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();
   
    public Profile setUp() {
        Profile profile = EasyMock.createMockBuilder(Profile.class)
                .addMockedMethod("fetch", String.class, String.class, String.class).createMock();
        profile.storage = new Storage();
        profile.storage.purgeSession();
        profile.storage.setSessionToken("token");
        profile.storage.setUsername("username");
        return profile;
    }

    @Test
    public void testCreateFilterReturnAllTracesmart() throws Exception {
        Profile profile = setUp();
        replay(profile);
        String result = WhiteboxImpl.invokeMethod(profile, "createFilter", Profile.FILTER_ALL);
        assertEquals(
                "state, user, details, document, badges, certificate, flags, facts, provider, cpr, kba, nemid, otp, personal",
                result);
    }

    @Test
    public void testCreateFilterReturnOneTracesmart() throws Exception {
        Profile profile = setUp();
        replay(profile);
        String result = WhiteboxImpl.invokeMethod(profile, "createFilter", Profile.FILTER_FACTS);
        assertEquals("facts", result);
    }

    @Test
    public void testCreateFilterReturnTwoTracesmart() throws Exception {
        Profile profile = setUp();
        replay(profile);
        String result = WhiteboxImpl.invokeMethod(profile, "createFilter",
                Profile.FILTER_STATE | Profile.FILTER_DETAILS);
        assertEquals("state, details", result);
    }

    @Test
    public void testRetrieveReturnJson()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, UnsupportedEncodingException {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        Profile profile = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"test\": \"result\"}");
        expect(profile.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(profile);
        assertEquals(json, profile.retrieve(Profile.FILTER_ALL, "user"));
    }

    @Test(expected = EmptySession.class)
    public void testRetrieveThrowsEmptySession()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, UnsupportedEncodingException {
        Profile profile = setUp();
        replay(profile);
        profile.storage.purgeSession();
        profile.retrieve(Check.TRACESMART_ADDRESS, "user");
    }

    @Test(expected = EmptyUsername.class)
    public void testRetrieveThrowsEmptyUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername, UnsupportedEncodingException {
        Profile profile = setUp();
        replay(profile);
        profile.storage.setUsername("");
        profile.retrieve(Check.TRACESMART_ADDRESS);
    }

    @Test(expected = InvalidUsername.class)
    public void testRetrieveThrowsInvalidUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, UnsupportedEncodingException {
        Profile profile = setUp();
        replay(profile);
        profile.retrieve(Check.TRACESMART_ADDRESS, "@123#");
    }
}