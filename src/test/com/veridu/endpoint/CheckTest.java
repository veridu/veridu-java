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
import org.powermock.reflect.internal.WhiteboxImpl;

import veridu.endpoint.Check;
import veridu.exceptions.APIError;
import veridu.exceptions.EmptyResponse;
import veridu.exceptions.EmptySession;
import veridu.exceptions.EmptyUsername;
import veridu.exceptions.InvalidFormat;
import veridu.exceptions.InvalidProvider;
import veridu.exceptions.InvalidResponse;
import veridu.exceptions.InvalidUsername;
import veridu.exceptions.NonceMismatch;
import veridu.exceptions.RequestFailed;
import veridu.exceptions.SignatureFailed;
import veridu.signature.Signature;
import veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Check.class)

public class CheckTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Check setUp() {
        Check check = EasyMock.createMockBuilder(Check.class)
                .addMockedMethod("signedFetch", String.class, String.class, String.class)
                .addMockedMethod("signedFetch", String.class, String.class).createMock();
        check.storage = new Storage();
        check.storage.purgeSession();
        check.storage.setSessionToken("token");
        check.storage.setUsername("username");
        return check;
    }

    @Test
    public void testCreateReturnsString() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, Exception {
        Check check = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"task_id\":\"task_id\"}");
        expect(check.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(check);
        assertEquals("task_id", check.create("tracesmart", Check.TRACESMART_ADDRESS));

    }

    @Test(expected = EmptySession.class)
    public void testCreateThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, Exception {
        Check check = setUp();
        replay(check);
        check.storage.purgeSession();
        check.create("tracesmart", Check.TRACESMART_ADDRESS);
    }

    @Test(expected = EmptyUsername.class)
    public void testCreateThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, Exception {
        Check check = setUp();
        replay(check);
        check.storage.setUsername("");
        check.create("tracesmart", Check.TRACESMART_ADDRESS);
    }

    @Test(expected = InvalidProvider.class)
    public void testCreatethrowsInvalidProvider() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, Exception {
        Check check = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"task_id\":\"1\"}");
        expect(check.signedFetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(check);
        assertEquals("1", check.create("invalidProvider", Check.TRACESMART_ADDRESS));

    }

    @Test(expected = InvalidUsername.class)
    public void testCreateThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, Exception {
        Check check = setUp();
        replay(check);
        check.create("tracesmart", Check.TRACESMART_ADDRESS, "@123#");
    }

    @Test
    public void testDetailsReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, Exception {
        Check check = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"json\":\"response\"}");
        expect(check.signedFetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(check);
        assertEquals(json, check.details("provider", Check.TRACESMART_ADDRESS, "username"));

    }

    @Test(expected = EmptySession.class)
    public void testDetailsThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, Exception {
        Check check = setUp();
        replay(check);
        check.storage.purgeSession();
        check.details("provider", Check.TRACESMART_ADDRESS, "username", Check.FILTER_ALL);
    }

    @Test(expected = InvalidUsername.class)
    public void testDetailsThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, Exception {
        Check check = setUp();
        replay(check);
        check.details("provider", Check.TRACESMART_ADDRESS, "@123#", Check.FILTER_ALL);
    }

    @Test
    public void testListAllReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, Exception {
        Check check = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"json\":\"response\"}");
        expect(check.signedFetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(check);
        assertEquals(json, check.listAll());

    }

    @Test(expected = EmptySession.class)
    public void testListAllThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, Exception {
        Check check = setUp();
        replay(check);
        check.storage.purgeSession();
        check.listAll();
    }

    @Test(expected = EmptyUsername.class)
    public void testListAllThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, Exception {
        Check check = setUp();
        replay(check);
        check.storage.setUsername("");
        check.listAll();
    }

    @Test(expected = InvalidUsername.class)
    public void testListAllThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, Exception {
        Check check = setUp();
        replay(check);
        check.listAll("@123#");
    }

    @Test
    public void testTracesmartSetupReturnAllTracesmart() throws Exception {
        Check check = setUp();
        replay(check);
        String result = WhiteboxImpl.invokeMethod(check, "tracesmartSetup", Check.TRACESMART_ALL);
        assertEquals("address,dob,driving,passport,credit-active", result);
    }

    @Test
    public void testTracesmartSetupReturnOneTracesmart() throws Exception {
        Check check = setUp();
        replay(check);
        String result = WhiteboxImpl.invokeMethod(check, "tracesmartSetup", Check.TRACESMART_PASSPORT);
        assertEquals("passport", result);
    }

    @Test
    public void testTracesmartSetupReturnTwoTracesmart() throws Exception {
        Check check = setUp();
        replay(check);
        String result = WhiteboxImpl.invokeMethod(check, "tracesmartSetup",
                Check.TRACESMART_ADDRESS | Check.TRACESMART_DOB);
        assertEquals("address,dob", result);
    }

}
