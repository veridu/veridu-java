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

import veridu.endpoint.Certificate;
import veridu.exceptions.APIError;
import veridu.exceptions.EmptyResponse;
import veridu.exceptions.EmptySession;
import veridu.exceptions.EmptyUsername;
import veridu.exceptions.InvalidCertificate;
import veridu.exceptions.InvalidFormat;
import veridu.exceptions.InvalidResponse;
import veridu.exceptions.InvalidUsername;
import veridu.exceptions.NonceMismatch;
import veridu.exceptions.RequestFailed;
import veridu.exceptions.SignatureFailed;
import veridu.signature.Signature;
import veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Certificate.class)
public class CertificateTest {
    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Certificate setUp() {
        Certificate certificate = EasyMock.createMockBuilder(Certificate.class)
                .addMockedMethod("fetch", String.class, String.class).createMock();
        certificate.storage = new Storage();
        certificate.storage.purgeSession();
        certificate.storage.setSessionToken("token");
        certificate.storage.setUsername("username");
        return certificate;
    }

    @Test
    public void testListAllReturnsArray() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        Certificate certificate = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"list\":[\"json\"]}");
        expect(certificate.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(certificate);
        JSONArray array = new JSONArray();
        array.add("json");
        assertEquals(array, certificate.listAll());
    }

    @Test(expected = EmptySession.class)
    public void testListAllThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Certificate certificate = setUp();
        replay(certificate);
        certificate.storage.purgeSession();
        certificate.listAll();
    }

    @Test(expected = EmptyUsername.class)
    public void testListAllThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Certificate certificate = setUp();
        replay(certificate);
        certificate.storage.setUsername("");
        certificate.listAll();
    }

    @Test(expected = InvalidUsername.class)
    public void testListAllThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        Certificate certificate = setUp();
        replay(certificate);
        certificate.listAll("@123#");
    }

    @Test
    public void testRetrieveReturnsArray()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidCertificate, InvalidUsername {
        Certificate certificate = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"certificate\":[\"response\"]}");
        expect(certificate.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(certificate);
        JSONArray array = (JSONArray) parser.parse("[\"response\"]");
        assertEquals(array, certificate.retrieve("mpaa"));
    }

    @Test(expected = EmptySession.class)
    public void testRetrieveThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidCertificate, InvalidUsername {
        Certificate certificate = setUp();
        replay(certificate);
        certificate.storage.purgeSession();
        certificate.retrieve("mpaa");
    }

    @Test(expected = EmptyUsername.class)
    public void testRetrieveThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidCertificate, InvalidUsername {
        Certificate certificate = setUp();
        replay(certificate);
        certificate.storage.setUsername("");
        certificate.retrieve("mpaa");
    }

    @Test(expected = InvalidUsername.class)
    public void testRetrieveThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidCertificate {
        Certificate certificate = setUp();
        replay(certificate);
        certificate.retrieve("mpaa", "@123#");
    }

}
