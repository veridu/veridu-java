package veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;

import org.easymock.EasyMock;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import veridu.endpoint.AbstractEndpoint;
import veridu.endpoint.User;
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
@PrepareForTest(User.class)

public class UserTest {
    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();
    
    public User setUp() {
        User user = EasyMock.createMockBuilder(User.class).addMockedMethod("fetch", String.class, String.class)
                .addMockedMethod("fetch", String.class, String.class, String.class)
                .addMockedMethod("signedFetch", String.class, String.class).createMock();
        user.storage = new Storage();
        user.storage.purgeSession();
        user.storage.setSessionToken("token");
        user.storage.setUsername("username");
        return user;
    }

    @Test
    public void testAttributeDetailsReturnsJson() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        User user = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"attribute\": {\"attribute\":\"score\"}}");
        expect(user.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(user);
        JSONObject obj = (JSONObject) parser.parse("{\"attribute\":\"score\"}");
        assertEquals(obj, user.attributeDetails("attribute", "user"));
    }

    @Test(expected = EmptySession.class)
    public void testAttributeDetailsThrowsEmptySession()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.purgeSession();
        user.attributeDetails("attribute", "user");
    }

    @Test(expected = EmptyUsername.class)
    public void testAttributeDetailsThrowsEmptyUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.setUsername("");
        user.attributeDetails("attribute");
    }

    @Test(expected = InvalidUsername.class)
    public void testAttributeDetailsThrowsInvalidUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        User user = setUp();
        user.attributeDetails("attribute", "@123#");
    }

    @Test
    public void testAttributeScoreReturnsString() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        User user = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"attribute\":\"2.3\"}");
        expect(user.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(user);
        assertEquals("2.3", user.attributeScore("attribute", "user"));
    }

    @Test(expected = EmptySession.class)
    public void testAttributeScoreThrowsEmptySession()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.purgeSession();
        user.attributeScore("attribute", "user");
    }

    @Test(expected = EmptyUsername.class)
    public void testAttributeScoreThrowsEmptyUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.setUsername("");
        user.attributeScore("attribute");
    }

    @Test(expected = InvalidUsername.class)
    public void testAttributeScoreThrowsInvalidUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        User user = setUp();
        user.attributeScore("attribute", "@123#");
    }

    @Test
    public void testAttributeValueReturnsString() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        User user = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"attribute\":\"attribute\"}");
        expect(user.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(user);
        assertEquals("attribute", user.attributeValue("attribute", "user"));
    }

    @Test(expected = EmptySession.class)
    public void testAttributeValueThrowsEmptySession()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        User user = setUp();
        replay(user);
        user.storage.purgeSession();
        user.attributeValue("attribute", "user");
    }

    @Test(expected = EmptyUsername.class)
    public void testAttributeValueThrowsEmptyUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.setUsername("");
        user.attributeValue("attribute");
    }

    @Test(expected = InvalidUsername.class)
    public void testAttributeValueThrowsInvalidUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        User user = setUp();
        user.attributeValue("attribute", "@123#");
    }

    @Test
    public void testCompareAttributeReturnJson() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        User user = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"test\":\"value\"}");
        expect(user.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(user);
        assertEquals(json, user.compareAttribute("attribute", "value", "user"));

    }

    @Test(expected = EmptySession.class)
    public void testCompareAttributeThrowsEmptySession()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.purgeSession();
        user.compareAttribute("attribute", "value", "user");
    }

    @Test(expected = EmptyUsername.class)
    public void testCompareAttributeThrowsEmptyUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.setUsername("");
        user.compareAttribute("attribute", "value");
    }

    @Test(expected = InvalidUsername.class)
    public void testCompareAttributeThrowsInvalidUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        User user = setUp();
        user.compareAttribute("attribute", "value", "@123#");
    }

    @Test
    public void testCreateReturnsBoolean() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, InvalidUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        User user = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"status\": \"true\"}");
        expect(user.signedFetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(user);
        assertTrue(user.create("user"));
        assertEquals("user", user.storage.getUsername());
    }

    @Test(expected = EmptySession.class)
    public void testCreateThrowsEmptySession() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, InvalidUsername {
        User user = setUp();
        replay(user);
        user.storage.purgeSession();
        user.create("user");
    }

    @Test(expected = InvalidUsername.class)
    public void testCreateThrowsInvalidUsername() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, InvalidUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        User user = setUp();
        replay(user);
        user.create("@123#");

    }

    @Test
    public void testGetAllAttributeScoresReturnsJson() throws ParseException, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        User user = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"profile\": {\"profile\":\"json\"}}");
        expect(user.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(user);
        JSONObject obj = (JSONObject) parser.parse("{\"profile\":\"json\"}");
        assertEquals(obj, user.getAllAttributeScores("user"));
    }

    @Test(expected = EmptySession.class)
    public void testGetAllAttributeScoresThrowsEmptySession()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.purgeSession();
        user.getAllAttributeScores("user");
    }

    @Test(expected = EmptyUsername.class)
    public void testGetAllAttributeScoresThrowsEmptyUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.setUsername("");
        user.getAllAttributeScores();
    }

    @Test(expected = InvalidUsername.class)
    public void testGetAllAttributeScoresThrowsInvalidUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        User user = setUp();
        user.getAllAttributeScores("@123#");
    }

    @Test
    public void testGetAllAttributeValuesReturnsJson() throws ParseException, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        User user = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"profile\": {\"profile\":\"json\"}}");
        expect(user.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(user);
        JSONObject obj = (JSONObject) parser.parse("{\"profile\":\"json\"}");
        assertEquals(obj, user.getAllAttributeValues("user"));
    }

    @Test(expected = EmptySession.class)
    public void testGetAllAttributeValuesThrowsEmptySession()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.purgeSession();
        user.getAllAttributeValues("user");
    }

    @Test(expected = EmptyUsername.class)
    public void testGetAllAttributeValuesThrowsEmptyUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.setUsername("");
        user.getAllAttributeValues();
    }

    @Test(expected = InvalidUsername.class)
    public void testGetAllAttributeValuesThrowsInvalidUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        User user = setUp();
        user.getAllAttributeValues("@123#");
    }

    @Test
    public void testGetAllDetailsReturnsJson() throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(true);
        PowerMock.replayAll();
        User user = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"profile\": {\"profile\":\"json\"}}");
        expect(user.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(user);
        JSONObject obj = (JSONObject) parser.parse("{\"profile\":\"json\"}");
        assertEquals(obj, user.getAllDetails("user"));
    }

    @Test(expected = EmptyUsername.class)
    public void testGetAllDetailsThrowEmptyUsername()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.setUsername("");
        user.getAllDetails();
    }

    @Test(expected = EmptySession.class)
    public void testGetAllDetailsThrowsEmptySession()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, EmptyUsername {
        User user = setUp();
        replay(user);
        user.storage.purgeSession();
        user.getAllDetails("user");
    }

    @Test(expected = InvalidUsername.class)
    public void testGetAllDetailsThrowsInvalidUsername() throws EmptySession, EmptyUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, InvalidUsername {
        mockStatic(AbstractEndpoint.class);
        expect(AbstractEndpoint.validateUsername(isA(String.class))).andReturn(false);
        PowerMock.replayAll();
        User user = setUp();
        user.getAllDetails("@123#");
    }

}