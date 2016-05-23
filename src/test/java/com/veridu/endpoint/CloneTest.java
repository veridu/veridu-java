package com.veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

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
@PrepareForTest(Clone.class)

public class CloneTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    @Test
    public void detailsReturnsJson() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, EmptySession, EmptyUsername, SignatureFailed, NonceMismatch, InvalidUsername {
        Clone clone = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"json\":\"response\"}");
        expect(clone.signedFetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(clone);
        assertEquals(json, clone.details());
    }

    @Test(expected = EmptyUsername.class)
    public void detailsThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, InvalidUsername, ParseException {
        Clone clone = setUp();
        clone.storage.setUsername("");
        replay(clone);
        clone.details();
    }

    @Test(expected = EmptySession.class)
    public void detailsThrowsEmpySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, InvalidUsername, ParseException {
        Clone clone = setUp();
        clone.storage.purgeSession();
        replay(clone);
        clone.details();
    }

    @Test(expected = InvalidUsername.class)
    public void detailsThrowsInvalidUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, SignatureFailed, NonceMismatch, ParseException {
        Clone clone = setUp();
        replay(clone);
        clone.details("@123#");
    }

    public Clone setUp() {
        Clone clone = EasyMock.createMockBuilder(Clone.class).addMockedMethod("signedFetch", String.class, String.class)
                .createMock();
        clone.storage = new Storage();
        clone.storage.purgeSession();
        clone.storage.setSessionToken("token");
        clone.storage.setUsername("username");
        return clone;
    }

}
