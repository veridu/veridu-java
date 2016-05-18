package veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import veridu.endpoint.Facts;
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
@PrepareForTest(Facts.class)
public class FactsTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Facts setUp() {
        Facts facts = EasyMock.createMockBuilder(Facts.class).addMockedMethod("fetch", String.class, String.class)
                .createMock();
        facts.storage = new Storage();
        facts.storage.purgeSession();
        facts.storage.setSessionToken("token");
        facts.storage.setUsername("username");
        return facts;
    }

    @Test
    public void testListAllReturnsArray() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        Facts facts = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"list\":[\"response\"]}");
        expect(facts.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(facts);
        JSONArray array = new JSONArray();
        array.add("response");
        assertEquals(array, facts.listAll());
    }

    @Test(expected = EmptySession.class)
    public void testListAllThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Facts facts = setUp();
        replay(facts);
        facts.storage.purgeSession();
        facts.listAll();
    }

    @Test(expected = EmptyUsername.class)
    public void testListAllThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Facts facts = setUp();
        replay(facts);
        facts.storage.setUsername("");
        facts.listAll();
    }

    @Test(expected = InvalidUsername.class)
    public void testListAllThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        Facts facts = setUp();
        replay(facts);
        facts.listAll("@123#");
    }

    @Test
    public void testRetrieveReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        Facts facts = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"facts\":{\"facebook\":\"json\"}}");
        expect(facts.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(facts);
        JSONObject obj = (JSONObject) parser.parse("{\"facebook\":\"json\"}");
        assertEquals(obj, facts.retrieve(Facts.PROVIDER_FACEBOOK));
    }

    @Test(expected = EmptySession.class)
    public void testRetrieveThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, InvalidUsername {
        Facts facts = setUp();
        replay(facts);
        facts.storage.purgeSession();
        facts.retrieve(Facts.PROVIDER_FACEBOOK);
    }

    @Test(expected = EmptyUsername.class)
    public void testRetrieveThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, InvalidUsername {
        Facts facts = setUp();
        replay(facts);
        facts.storage.setUsername("");
        facts.retrieve(Facts.PROVIDER_FACEBOOK);
    }

    @Test(expected = InvalidUsername.class)
    public void testRetrieveThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, EmptyUsername {
        Facts facts = setUp();
        replay(facts);
        facts.retrieve(Facts.PROVIDER_TWITTER, "@123#");
    }
}
