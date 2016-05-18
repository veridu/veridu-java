package veridu.endpoint;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import veridu.endpoint.Batch;
import veridu.exceptions.APIError;
import veridu.exceptions.EmptyResponse;
import veridu.exceptions.EmptySession;
import veridu.exceptions.EmptyUsername;
import veridu.exceptions.InvalidFormat;
import veridu.exceptions.InvalidResponse;
import veridu.exceptions.NonceMismatch;
import veridu.exceptions.RequestFailed;
import veridu.exceptions.SignatureFailed;
import veridu.signature.Signature;
import veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Batch.class)
public class BatchTest {
    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public Batch setUp() {
        Batch batch = EasyMock.createMockBuilder(Batch.class)
                .addMockedMethod("fetch", String.class, String.class, String.class).createMock();
        batch.storage = new Storage();
        batch.storage.purgeSession();
        batch.storage.setSessionToken("token");
        batch.storage.setUsername("username");
        return batch;
    }

    @Test
    public void testRequestReturnsArray()
            throws ParseException, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, UnsupportedEncodingException {
        Batch batch = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"batch\":[\"json\"]}");
        expect(batch.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(batch);
        JSONArray array = new JSONArray();
        array.add("json");

        assertEquals(array, batch.request(new ArrayList<JSONObject>()));
    }

    @Test(expected = EmptySession.class)
    public void testRequestThrowsEmptySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, ParseException, UnsupportedEncodingException {
        Batch batch = setUp();
        replay(batch);
        batch.storage.purgeSession();
        List<JSONObject> jobs = new ArrayList<JSONObject>();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"method\": \"get\", \"resource\": \"resource\"}");
        jobs.add(json);
        assertEquals("{\"json\":\"response\"}", batch.request(jobs));
        batch.request(jobs);
    }
}
