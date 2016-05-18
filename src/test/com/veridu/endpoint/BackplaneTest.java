package veridu.endpoint;

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

import veridu.endpoint.Backplane;
import veridu.exceptions.APIError;
import veridu.exceptions.EmptyResponse;
import veridu.exceptions.EmptySession;
import veridu.exceptions.EmptyUsername;
import veridu.exceptions.InvalidFormat;
import veridu.exceptions.InvalidResponse;
import veridu.exceptions.InvalidUsername;
import veridu.exceptions.RequestFailed;
import veridu.signature.Signature;
import veridu.storage.Storage;

public class BackplaneTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Backplane setUp() {
        Backplane backplane = EasyMock.createMockBuilder(Backplane.class)
                .addMockedMethod("fetch", String.class, String.class, String.class).createMock();
        backplane.storage = new Storage();
        backplane.storage.purgeSession();
        backplane.storage.setSessionToken("token");
        backplane.storage.setUsername("username");
        return backplane;
    }

    @Test
    public void testSetupReturnsBoolean() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        Backplane backplane = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(backplane.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(backplane);
        assertEquals(true, backplane.setup("channel"));
    }

    @Test(expected = EmptySession.class)
    public void testSetupThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        Backplane backplane = setUp();
        backplane.storage.purgeSession();
        backplane.setup("channel");
    }

    @Test(expected = EmptyUsername.class)
    public void testRetrieveThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        Backplane backplane = setUp();
        replay(backplane);
        backplane.storage.setUsername("");
        backplane.setup("channel");
    }

    @Test(expected = InvalidUsername.class)
    public void testRetrieveThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptyUsername, UnsupportedEncodingException {
        Backplane backplane = setUp();
        replay(backplane);
        backplane.setup("channel", "@123#");
    }
}
