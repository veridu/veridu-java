package veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.easymock.EasyMock;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import veridu.endpoint.Badge;
import veridu.endpoint.Personal;
import veridu.exceptions.APIError;
import veridu.exceptions.EmptyResponse;
import veridu.exceptions.EmptySession;
import veridu.exceptions.EmptyUsername;
import veridu.exceptions.InvalidFormat;
import veridu.exceptions.InvalidResponse;
import veridu.exceptions.InvalidUsername;
import veridu.exceptions.NonceMismatch;
import veridu.exceptions.RequestFailed;
import veridu.exceptions.SignatureFailed;
import veridu.signature.Signature;
import veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Badge.class)
public class PersonalTest {

    public static Storage storage = new Storage();
    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Personal setUp() {
        Personal personal = EasyMock.createMockBuilder(Personal.class)
                .addMockedMethod("fetch", String.class, String.class)
                .addMockedMethod("fetch", String.class, String.class, String.class)
                .addMockedMethod("signedFetch", String.class, String.class, String.class).createMock();
        personal.storage = new Storage();
        personal.storage.purgeSession();
        personal.storage.setSessionToken("token");
        personal.storage.setUsername("username");
        return personal;
    }

    @Test
    public void testCreateReturnsJson()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        Personal personal = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"json\":\"response\"}");
        expect(personal.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(personal);
        assertEquals(json, personal.create(new HashMap<String, String>()));

    }

    @Test(expected = EmptySession.class)
    public void testCreateThrowsEmptySession()
            throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed,
            SignatureFailed, NonceMismatch, InvalidUsername, UnsupportedEncodingException {
        Personal personal = setUp();
        replay(personal);
        personal.storage.purgeSession();
        personal.create(new HashMap<String, String>());
    }

    @Test(expected = EmptyUsername.class)
    public void testCreateThrowsEmptyUsername()
            throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed,
            SignatureFailed, NonceMismatch, InvalidUsername, UnsupportedEncodingException {
        Personal personal = setUp();
        replay(personal);
        personal.storage.setUsername("");
        personal.create(new HashMap<String, String>());
    }

    @Test(expected = InvalidUsername.class)
    public void testCreateThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, UnsupportedEncodingException {
        Personal personal = setUp();
        replay(personal);
        personal.create(new HashMap<String, String>(), "@123#");
    }

    @Test
    public void testRetrieveReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        Personal personal = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"state\":\"true\"}");
        expect(personal.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(personal);
        assertEquals(json, personal.retrieve(Personal.TYPE_STATE, "username"));
    }

    @Test(expected = EmptySession.class)
    public void testRetrieveThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, InvalidUsername {
        Personal personal = setUp();
        replay(personal);
        personal.storage.purgeSession();
        personal.retrieve(Personal.TYPE_STATE, "username");
    }

    @Test(expected = EmptyUsername.class)
    public void testRetrieveThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, InvalidUsername {
        Personal personal = setUp();
        replay(personal);
        personal.storage.setUsername("");
        personal.retrieve();
    }

    @Test(expected = InvalidUsername.class)
    public void testRetrieveThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, EmptyUsername {
        Personal personal = setUp();
        replay(personal);
        personal.retrieve(Personal.TYPE_STATE, "@123#");
    }

    @Test
    public void testUpdateReturnsJson()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        Personal personal = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"fields\":\"fieldA\"}");
        expect(personal.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(personal);
        assertEquals("fieldA", personal.update(new HashMap<String, String>()));
    }

    @Test(expected = EmptySession.class)
    public void testUpdateThrowsEmptySession()
            throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed,
            SignatureFailed, NonceMismatch, InvalidUsername, UnsupportedEncodingException {
        Personal personal = setUp();
        replay(personal);
        personal.storage.purgeSession();
        personal.update(new HashMap<String, String>());
    }

    @Test(expected = EmptyUsername.class)
    public void testUpdateThrowsEmptyUsername()
            throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed,
            SignatureFailed, NonceMismatch, InvalidUsername, UnsupportedEncodingException {
        Personal personal = setUp();
        replay(personal);
        personal.storage.setUsername("");
        personal.update(new HashMap<String, String>());
    }

    @Test(expected = InvalidUsername.class)
    public void testUpdateThrowsInvalidUsername()
            throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, EmptyUsername, SignatureFailed, NonceMismatch, UnsupportedEncodingException {
        Personal personal = setUp();
        replay(personal);
        personal.update(new HashMap<String, String>(), "@123#");
    }

}
