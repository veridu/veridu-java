package veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import veridu.endpoint.Session;
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
@PrepareForTest(Session.class)
public class SessionTest {
    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Session setUp() {
        Session session = EasyMock.createMockBuilder(Session.class)
                .addMockedMethod("signedFetch", String.class, String.class)
                .addMockedMethod("signedFetch", String.class, String.class, String.class).createMock();
        session.storage = new Storage();
        session.storage.purgeSession();
        session.storage.setSessionToken("token");
        session.storage.setUsername("username");
        return session;
    }

    @Test
    public void testCreateReadOnlyFalse() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        Session session = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser
                .parse("{\"expires\":1455540581,\"status\":true," + "\"token\":\"token123\", \"nonce\": \"nonce123\"}");
        expect(session.signedFetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(session);
        assertTrue(session.create(false));
    }

    @Test
    public void testCreateReadOnlyTrue() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        Session session = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser
                .parse("{\"expires\":1455540581,\"status\":true," + "\"token\":\"token123\", \"nonce\": \"nonce123\"}");
        expect(session.signedFetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(session);
        assertTrue(session.create(true));
    }

    @Test
    public void testExpireReturnNull() throws SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptySession {
        Session session = setUp();
        expect(session.signedFetch(isA(String.class), isA(String.class))).andReturn(new JSONObject());
        assertTrue(session.expire());
    }

    @Test(expected = EmptySession.class)
    public void testExpireThrowsEmptySession() throws RequestFailed, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, EmptySession {
        Session session = setUp();
        session.storage.purgeSession();
        session.expire();
    }

    @Test
    public void testExtendReturnSession() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession {
        Session session = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser
                .parse("{\"expires\":1455540581,\"status\":true," + "\"token\":\"token123\", \"nonce\": \"nonce123\"}");
        expect(session.signedFetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(session);
        assertTrue(session.extend());
    }

    @Test(expected = EmptySession.class)
    public void testExtendThrowsEmptySession() throws RequestFailed, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, EmptySession {
        Session session = setUp();
        session.storage.purgeSession();
        session.extend();
    }

}
