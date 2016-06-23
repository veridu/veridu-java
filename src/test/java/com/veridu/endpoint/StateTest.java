package com.veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;

import org.easymock.EasyMock;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.veridu.endpoint.AbstractEndpoint;
import com.veridu.endpoint.State;
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
@PrepareForTest(State.class)
public class StateTest {

    String key = "key";
    String secret = "secret";
    String version = "version";

    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public State setUp() {
        State state = EasyMock.createMockBuilder(State.class).addMockedMethod("fetch", String.class, String.class)
                .addMockedMethod("fetch", String.class, String.class, String.class)
                .addMockedMethod("signedFetch", String.class, String.class).createMock();
        state.storage = new Storage();
        state.storage.purgeSession();
        state.storage.setSessionToken("token");
        state.storage.setUsername("username");
        return state;
    }

    @Test
    public void testRetrievePassingUsernameReturnsString() throws ParseException, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptyUsername, Exception {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        State state = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser
                .parse("{\"expires\":1455540581,\"status\":true," + "\"token\":\"token123\", \"state\": \"state123\"}");
        expect(state.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(state);
        assertEquals("state123", state.retrieve("username"));
    }

    @Test(expected = EmptySession.class)
    public void testRetrievePassingUsernameThrowsEmptySession() throws ParseException, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptyUsername, InvalidUsername, EmptySession {
        State state = setUp();
        state.storage.purgeSession();
        replay(state);
        state.retrieve("username");
    }

    @Test(expected = InvalidUsername.class)
    public void testRetrievePassingUsernameThrowsInvalidUsername() throws ParseException, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptyUsername, InvalidUsername, EmptySession {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        State state = setUp();
        replay(state);
        state.retrieve("@123Username#");
    }

    @Test
    public void testRetrieveStorageUsernameReturnsString() throws ParseException, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptyUsername, EmptySession, InvalidUsername {
        State state = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser
                .parse("{\"expires\":1455540581,\"status\":true," + "\"token\":\"token123\", \"state\": \"state123\"}");
        expect(state.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(state);
        assertEquals("state123", state.retrieve());
    }

    @Test(expected = EmptyUsername.class)
    public void testRetrieveStorageUsernameThrowsEmptyUsername() throws ParseException, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptyUsername, EmptySession, InvalidUsername {
        State state = setUp();
        state.storage.setUsername("");
        replay(state);
        state.retrieve();
    }

}
