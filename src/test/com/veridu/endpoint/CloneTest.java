package veridu.endpoint;

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

import veridu.endpoint.Clone;
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
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, InvalidUsername {
        Clone clone = setUp();
        clone.storage.setUsername("");
        replay(clone);
        clone.details();
    }

    @Test(expected = EmptySession.class)
    public void detailsThrowsEmpySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, InvalidUsername {
        Clone clone = setUp();
        clone.storage.purgeSession();
        replay(clone);
        clone.details();
    }

    @Test(expected = InvalidUsername.class)
    public void detailsThrowsInvalidUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, SignatureFailed, NonceMismatch {
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
