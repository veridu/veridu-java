package veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import veridu.endpoint.OTP;
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
@PrepareForTest(OTP.class)
public class OTPTest {
    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public OTP setUp() {
        OTP otp = EasyMock.createMockBuilder(OTP.class).addMockedMethod("fetch", String.class, String.class)
                .addMockedMethod("fetch", String.class, String.class, String.class)
                .addMockedMethod("signedFetch", String.class, String.class, String.class).createMock();
        otp.storage = new Storage();
        otp.storage.purgeSession();
        otp.storage.setSessionToken("token");
        otp.storage.setUsername("username");
        return otp;
    }

    @Test
    public void testListAllReturnsArray() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        OTP otp = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"list\":[\"json\"]}");
        expect(otp.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(otp);
        JSONArray array = new JSONArray();
        array.add("json");
        assertEquals(array, otp.listAll());
    }

    @Test(expected = EmptySession.class)
    public void testListAllThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, InvalidUsername {
        OTP otp = setUp();
        replay(otp);
        otp.storage.purgeSession();
        otp.listAll();
    }

    @Test(expected = EmptyUsername.class)
    public void testListAllThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch, InvalidUsername {
        OTP otp = setUp();
        replay(otp);
        otp.storage.setUsername("");
        otp.listAll();
    }

    @Test(expected = InvalidUsername.class)
    public void testListAllThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptyUsername, Exception {
        OTP otp = setUp();
        replay(otp);
        otp.listAll("@123#");
    }

    @Test
    public void testCreateEmailReturnsBoolean()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(otp.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(otp);
        assertTrue(otp.createEmail("email", true, "callbackURL"));
    }

    @Test(expected = EmptySession.class)
    public void testCreateEmailThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.purgeSession();
        otp.createEmail("email", true, "callbackURL");
    }

    @Test(expected = EmptyUsername.class)
    public void testCreateEmailThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.setUsername("");
        otp.createEmail("emal", true, "callbackURL");
    }

    @Test(expected = InvalidUsername.class)
    public void testCreateEmailThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptyUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.createEmail("email", true, "callbackURL", "@123#");
    }

    @Test
    public void testCreateSMSReturnsBoolean()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(otp.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(otp);
        assertTrue(otp.createSMS("+codeDDDNumber"));
    }

    @Test(expected = EmptySession.class)
    public void testCreateSMSThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.purgeSession();
        otp.createSMS("+codeDDDNumber");
    }

    @Test(expected = EmptyUsername.class)
    public void testCreateSMSThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.setUsername("");
        otp.createSMS("+codeDDDNumber");
    }

    @Test(expected = InvalidUsername.class)
    public void testCreateSMSThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptyUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.createSMS("+codeDDDNumber", "@123#");
    }

    @Test
    public void testResendEmailReturnsBoolean()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(otp.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(otp);
        assertTrue(otp.resendEmail("email"));
    }

    @Test(expected = EmptySession.class)
    public void testResendEmailThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.purgeSession();
        otp.resendEmail("email");
    }

    @Test(expected = EmptyUsername.class)
    public void testResendEmailThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.setUsername("");
        otp.resendEmail("email");
    }

    @Test(expected = InvalidUsername.class)
    public void testResendEmailThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptyUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.resendEmail("email", "@123#");
    }

    @Test
    public void testResendSMSReturnsBoolean()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(otp.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(otp);
        assertTrue(otp.resendSMS("+codeDDDNumber"));
    }

    @Test(expected = EmptySession.class)
    public void testResendSMSThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.purgeSession();
        otp.resendSMS("+codeDDDNumber");
    }

    @Test(expected = EmptyUsername.class)
    public void testResendSMSThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.setUsername("");
        otp.resendSMS("+codeDDDNumber");
    }

    @Test(expected = InvalidUsername.class)
    public void testResendSMSThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptyUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.resendSMS("+codeDDDNumber", "@123#");
    }

    @Test
    public void testCheckEmailReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        OTP otp = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(otp.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(otp);
        assertTrue(otp.checkEmail("email"));
    }

    @Test(expected = EmptySession.class)
    public void testCheckEmailThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        OTP otp = setUp();
        replay(otp);
        otp.storage.purgeSession();
        otp.checkEmail("email");
    }

    @Test(expected = EmptyUsername.class)
    public void testCheckEmailThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        OTP otp = setUp();
        replay(otp);
        otp.storage.setUsername("");
        otp.checkEmail("email");
    }

    @Test(expected = InvalidUsername.class)
    public void testCheckEmailThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptyUsername {
        OTP otp = setUp();
        replay(otp);
        otp.checkEmail("email", "@123#");
    }

    @Test
    public void testCheckSMSReturnsBoolean()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(otp.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(otp);
        assertTrue(otp.checkSMS("+codeDDDNumber"));
    }

    @Test(expected = EmptySession.class)
    public void testCheckSMSThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.purgeSession();
        otp.checkSMS("+codeDDDNumber");
    }

    @Test(expected = EmptyUsername.class)
    public void testCheckSMSThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.setUsername("");
        otp.checkSMS("+codeDDDNumber");
    }

    @Test(expected = InvalidUsername.class)
    public void testCheckSMSThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptyUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.checkSMS("+codeDDDNumber", "@123#");
    }

    @Test
    public void testVerifyEmailReturnsBoolean()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(otp.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(otp);
        assertTrue(otp.verifyEmail("email", "code"));
    }

    @Test(expected = EmptySession.class)
    public void testVerifyEmailThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.purgeSession();
        otp.verifyEmail("email", "code");
    }

    @Test(expected = EmptyUsername.class)
    public void testVerifyEmailThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.setUsername("");
        otp.verifyEmail("email", "code");
    }

    @Test(expected = InvalidUsername.class)
    public void testVerifyEmailThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptyUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.verifyEmail("email", "code", "@123#");
    }

    @Test
    public void testVerifySMSReturnsBoolean()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\":\"true\"}");
        expect(otp.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(otp);
        assertTrue(otp.verifySMS("+codeDDDNumber", "code"));
    }

    @Test(expected = EmptySession.class)
    public void testVerifySMSThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.purgeSession();
        otp.verifySMS("+codeDDDNumber", "code");
    }

    @Test(expected = EmptyUsername.class)
    public void testVerifySMSThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.storage.setUsername("");
        otp.verifySMS("+codeDDDNumber", "code");
    }

    @Test(expected = InvalidUsername.class)
    public void testVerifySMSThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptyUsername, UnsupportedEncodingException {
        OTP otp = setUp();
        replay(otp);
        otp.verifySMS("+codeDDDNumber", "code", "@123#");
    }

    @Test
    public void testVerifiedEmailReturnsBoolean() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        OTP otp = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"state\":\"true\"}");
        expect(otp.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(otp);
        assertTrue(otp.verifiedEmail());
    }

    @Test(expected = EmptySession.class)
    public void testVerifiedEmailThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        OTP otp = setUp();
        replay(otp);
        otp.storage.purgeSession();
        otp.verifiedEmail();
    }

    @Test(expected = EmptyUsername.class)
    public void testVerifiedEmailThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        OTP otp = setUp();
        replay(otp);
        otp.storage.setUsername("");
        otp.verifiedEmail();
    }

    @Test(expected = InvalidUsername.class)
    public void testVerifiedEmailThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptyUsername {
        OTP otp = setUp();
        replay(otp);
        otp.verifiedEmail("@123#");
    }

    @Test
    public void testVerifiedSMSReturnsBoolean() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        OTP otp = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"state\":\"true\"}");
        expect(otp.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(otp);
        assertTrue(otp.verifiedSMS());
    }

    @Test(expected = EmptySession.class)
    public void testVerifiedSMSThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        OTP otp = setUp();
        replay(otp);
        otp.storage.purgeSession();
        otp.verifiedSMS();
    }

    @Test(expected = EmptyUsername.class)
    public void testVerifiedSMSThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        OTP otp = setUp();
        replay(otp);
        otp.storage.setUsername("");
        otp.verifiedSMS();
    }

    @Test(expected = InvalidUsername.class)
    public void testVerifiedSMSThrowsInvalidUsername() throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptyUsername {
        OTP otp = setUp();
        replay(otp);
        otp.verifiedSMS("@123#");
    }
}
