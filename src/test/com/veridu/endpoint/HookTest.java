package veridu.endpoint;

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

import veridu.endpoint.Hook;
import veridu.exceptions.APIError;
import veridu.exceptions.EmptyResponse;
import veridu.exceptions.EmptySession;
import veridu.exceptions.InvalidFormat;
import veridu.exceptions.InvalidResponse;
import veridu.exceptions.NonceMismatch;
import veridu.exceptions.RequestFailed;
import veridu.exceptions.SignatureFailed;
import veridu.signature.Signature;
import veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Hook.class)

public class HookTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Hook setUp() {
        Hook hook = EasyMock.createMockBuilder(Hook.class)
                .addMockedMethod("signedFetch", String.class, String.class, String.class)
                .addMockedMethod("signedFetch", String.class, String.class).createMock();
        hook.storage = new Storage();
        hook.storage.purgeSession();
        hook.storage.setSessionToken("token");
        hook.storage.setUsername("username");
        return hook;
    }

    @Test
    public void testCreateReturnsJson() throws SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, ParseException, EmptySession, UnsupportedEncodingException {
        Hook hook = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"json\":\"response\"}");
        expect(hook.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(hook);
        assertEquals(json, hook.create("trigger", "http://url.com"));
    }

    @Test(expected = EmptySession.class)
    public void testCreateThrowsEmptySession() throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        Hook hook = setUp();
        replay(hook);
        hook.storage.purgeSession();
        hook.create("trigger", "http://url.com");
    }

    @Test
    public void testDetailsReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession {
        Hook hook = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"details\":{\"json\":\"response\"}}");
        expect(hook.signedFetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(hook);
        JSONObject obj = (JSONObject) parser.parse("{\"json\":\"response\"}");
        assertEquals(obj, hook.details(123));
    }

    @Test(expected = EmptySession.class)
    public void testDetailsThrowsEmptySession() throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        Hook hook = setUp();
        replay(hook);
        hook.storage.purgeSession();
        hook.details(123);
    }

    @Test
    public void testListReturnsJson() throws SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptySession, ParseException {
        Hook hook = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"list\":[\"json\"]}");
        expect(hook.signedFetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(hook);
        JSONArray array = new JSONArray();
        array.add("json");
        assertEquals(array, hook.list());
    }

    @Test(expected = EmptySession.class)
    public void testListThrowsEmptySession() throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        Hook hook = setUp();
        replay(hook);
        hook.storage.purgeSession();
        hook.list();
    }
}
