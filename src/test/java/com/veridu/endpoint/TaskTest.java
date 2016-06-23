package com.veridu.endpoint;

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

import com.veridu.endpoint.Task;
import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.EmptyUsername;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.InvalidUsername;
import com.veridu.exceptions.RequestFailed;
import com.veridu.signature.Signature;
import com.veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Task.class)

public class TaskTest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();
    
    @Test
    public void detailsReturnsJson() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        Task task = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"info\":{\"info\":\"info\"}}");
        expect(task.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(task);
        JSONObject obj = (JSONObject) parser.parse("{\"info\":\"info\"}");
        assertEquals(obj, task.details(123));
    }

    @Test(expected = EmptyUsername.class)
    public void detailsThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Task task = setUp();
        task.storage.setUsername("");
        replay(task);
        task.details(123);
    }

    @Test(expected = EmptySession.class)
    public void detailsThrowsEmpySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Task task = setUp();
        task.storage.purgeSession();
        replay(task);
        task.details(123);
    }

    @Test
    public void listAllReturnsArray() throws ParseException, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        Task task = setUp();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"list\":[\"response\"]}");
        expect(task.fetch(isA(String.class), isA(String.class))).andReturn(json);
        replay(task);
        JSONArray array = new JSONArray();
        array.add("response");
        assertEquals(array, task.listAll());
    }

    @Test(expected = EmptyUsername.class)
    public void listAllThrowsEmptyUsername() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Task task = setUp();
        task.storage.setUsername("");
        replay(task);
        task.listAll();
    }

    @Test(expected = EmptySession.class)
    public void listAllThrowsEmpySession() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        Task task = setUp();
        task.storage.purgeSession();
        replay(task);
        task.listAll();
    }

    public Task setUp() {
        Task task = EasyMock.createMockBuilder(Task.class).addMockedMethod("fetch", String.class, String.class)
                .createMock();
        task.storage = new Storage();
        task.storage.purgeSession();
        task.storage.setSessionToken("token");
        task.storage.setUsername("username");
        return task;
    }

}
