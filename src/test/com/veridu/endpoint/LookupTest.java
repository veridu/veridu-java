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

import veridu.endpoint.Lookup;
import veridu.exceptions.APIError;
import veridu.exceptions.EmptyResponse;
import veridu.exceptions.EmptySession;
import veridu.exceptions.InvalidFormat;
import veridu.exceptions.InvalidResponse;
import veridu.exceptions.NonceMismatch;
import veridu.exceptions.RequestFailed;
import veridu.exceptions.SignatureFailed;
import veridu.exceptions.UnavailableRegion;
import veridu.signature.Signature;
import veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Lookup.class)
public class LookupTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Lookup setUp() {
        Lookup lookup = EasyMock.createMockBuilder(Lookup.class).addMockedMethod("fetch", String.class, String.class)
                .createMock();
        lookup.storage = new Storage();
        lookup.storage.purgeSession();
        lookup.storage.setSessionToken("token");
        lookup.storage.setUsername("username");
        return lookup;
    }

    @Test
    public void testRetrieveAddressInfoReturnsJson() throws SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, ParseException, EmptySession, UnavailableRegion {
        Lookup lookup = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"info\":{\"json\":\"response\"}}");
        expect(lookup.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(lookup);
        JSONObject obj = (JSONObject) parser.parse("{\"json\":\"response\"}");
        assertEquals(obj, lookup.retrieveAddressInfo("uk", 123));
    }

    @Test(expected = EmptySession.class)
    public void testRetrieveAddressInfoThrowsEmptySession() throws EmptySession, SignatureFailed, NonceMismatch,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnavailableRegion {
        Lookup lookup = setUp();
        replay(lookup);
        lookup.storage.purgeSession();
        lookup.retrieveAddressInfo("uk", 123);
    }

    @Test
    public void testRetrievePostCodeInfoReturnsArray() throws SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, ParseException, EmptySession, UnavailableRegion {
        Lookup lookup = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"results\":[\"json\"]}");
        expect(lookup.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(lookup);
        JSONArray array = new JSONArray();
        array.add("json");
        assertEquals(array, lookup.retrievePostCodeInfo("uk", "postcode"));
    }

    @Test(expected = EmptySession.class)
    public void testRetrievePostCodeInfoThrowsEmptySession() throws EmptySession, SignatureFailed, NonceMismatch,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnavailableRegion {
        Lookup lookup = setUp();
        replay(lookup);
        lookup.storage.purgeSession();
        lookup.retrievePostCodeInfo("uk", "postcode");
    }
}
