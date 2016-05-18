package com.veridu.endpoint;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.HashMap;

import org.easymock.EasyMock;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.WhiteboxImpl;

import com.veridu.endpoint.AbstractEndpoint;
import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.NonceMismatch;
import com.veridu.exceptions.RequestFailed;
import com.veridu.exceptions.SignatureFailed;
import com.veridu.signature.Signature;
import com.veridu.storage.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AbstractEndpoint.class)
public class AbstractEndpointTest {
    String key = "key";
    String secret = "secret";
    String version = "version";
    public static Storage storage = new Storage();
    Signature signature = EasyMock.createMockBuilder(Signature.class).addMockedMethod("signRequest").createMock();

    public AbstractEndpoint setUp() {
        AbstractEndpoint endpoint = EasyMock.createMockBuilder(AbstractEndpoint.class).addMockedMethod("request")
                .createMock();
        endpoint.storage = AbstractEndpointTest.storage;
        endpoint.storage.purgeSession();
        endpoint.storage.setSessionToken("token");
        endpoint.storage.setUsername("username");
        return endpoint;
    }

    @Test
    public void testQueryBuilderReturnsEmpty() throws Exception {
        AbstractEndpoint endpoint = setUp();
        HashMap<String, String> data = new HashMap<>();
        String result = WhiteboxImpl.invokeMethod(endpoint, "queryBuilder", data);
        assertEquals("", result);
    }

    @Test
    public void testQueryBuilderReturnsString() throws Exception {
        AbstractEndpoint endpoint = setUp();
        HashMap<String, String> data = new HashMap<>();
        data.put("data", "data");
        String result = WhiteboxImpl.invokeMethod(endpoint, "queryBuilder", data);
        assertEquals("&data=data", result);
    }

    @Test(expected = UnsupportedEncodingException.class)
    public void testQueryBuilderThrowsUnsupportedEncodingException() throws Exception {
        AbstractEndpoint endpoint = setUp();
        HashMap<String, String> data = new HashMap<>();
        // .getBytes("") raizes exception
        data.put("test", Arrays.toString("test".getBytes("")));
        WhiteboxImpl.invokeMethod(endpoint, "queryBuilder", data);

    }

    @Test
    public void testValidateUsernameValidUsername() {
        assertTrue(AbstractEndpoint.validateUsername("Jondoe_123"));
        assertTrue(AbstractEndpoint.validateUsername("jon_Jon"));
        assertTrue(AbstractEndpoint.validateUsername("jon123"));
        assertTrue(AbstractEndpoint.validateUsername("jonjon"));
        assertTrue(AbstractEndpoint.validateUsername("jonJon"));

    }

    @Test
    public void testValidateUsernameInvalidUsername() {
        assertFalse(AbstractEndpoint.validateUsername("@123#"));
        assertFalse(AbstractEndpoint.validateUsername("Test_@"));
    }

    @Test
    public void testFetchValidResponse()
            throws EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, ParseException {
        AbstractEndpoint endpoint = setUp();
        expect(endpoint.request(isA(String.class), isA(String.class), isA(String.class)))
                .andReturn("{\"expires\":1455540581,\"status\":true,\"token\":\"token\"}");
        replay(endpoint);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse("{\"expires\":1455540581,\"status\":true,\"token\":\"token\"}");
        assertEquals(json, endpoint.fetch("GET", "test/user", ""));
    }

    @Test(expected = InvalidFormat.class)
    public void testFetchInvalidFormat() throws InvalidResponse, RequestFailed, EmptyResponse, InvalidFormat, APIError {
        AbstractEndpoint endpoint = setUp();
        expect(endpoint.request(isA(String.class), isA(String.class), isA(String.class)))
                .andReturn("Response (on invalid format)");
        replay(endpoint);
        endpoint.fetch("GET", "test/user", "");
    }

    @Test(expected = InvalidResponse.class)
    public void testFetchInvalidResponse()
            throws InvalidResponse, RequestFailed, EmptyResponse, InvalidFormat, InvalidResponse, APIError {
        AbstractEndpoint endpoint = setUp();
        expect(endpoint.request(isA(String.class), isA(String.class), isA(String.class)))
                .andReturn("{\"expires\":1455540581,\"token\":\"token\"}");
        replay(endpoint);
        endpoint.fetch("GET", "test/user", "");
    }

    @Test(expected = EmptyResponse.class)
    public void testFetchEmptyResponse() throws RequestFailed, EmptyResponse, InvalidFormat, InvalidResponse, APIError {
        AbstractEndpoint endpoint = setUp();
        expect(endpoint.request(isA(String.class), isA(String.class), isA(String.class))).andReturn("");
        replay(endpoint);
        endpoint.fetch("GET", "test/user", "");
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testFetchAPIError() throws InvalidResponse, RequestFailed, EmptyResponse, InvalidFormat, APIError {
        expectedEx.expect(APIError.class);
        expectedEx.expectMessage("Session token is invalid");
        AbstractEndpoint endpoint = setUp();
        expect(endpoint.request(isA(String.class), isA(String.class), isA(String.class)))
                .andReturn("{\"status\":false,\"error\":{\"code\":400,\"type\":\"SESSION_TOKEN_INVALID\","
                        + "\"message\":\"Session token is invalid.\"}}");
        replay(endpoint);
        endpoint.fetch("GET", "test/user", "");
    }

    @Test
    public void testSignedFetchValidResponse()
            throws SignatureFailed, ParseException, ParseException, EmptyResponse, InvalidFormat, InvalidResponse,
            RequestFailed, APIError, SignatureFailed, NonceMismatch, SignatureException, UnsupportedEncodingException {
        AbstractEndpoint endpoint = EasyMock.createMockBuilder(AbstractEndpoint.class).addMockedMethod("request")
                .addMockedMethod("generateNonce").addMockedMethod("fetch", String.class, String.class, String.class)
                .createMock();
        String method = "GET";
        String resource = "test/user";
        JSONParser parser = new JSONParser();
        endpoint.signature = this.signature;
        JSONObject json = (JSONObject) parser
                .parse("{\"expires\":1455540581,\"status\":true," + "\"token\":\"token\",\"nonce\":\"nonce\"}");
        expect(endpoint.generateNonce()).andReturn("nonce");
        expect(endpoint.signature.signRequest(isA(String.class), isA(String.class), isA(String.class)))
                .andReturn("signature");
        expect(endpoint.fetch(isA(String.class), isA(String.class), anyString())).andReturn(json);
        replay(endpoint);
        assertEquals(json, endpoint.signedFetch(method, resource, ""));
    }

    @Test(expected = SignatureFailed.class)
    public void testSignedFetchInvalidSignature() throws SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureException {
        AbstractEndpoint endpoint = EasyMock.createMockBuilder(AbstractEndpoint.class).addMockedMethod("request")
                .addMockedMethod("generateNonce").addMockedMethod("fetch", String.class, String.class, String.class)
                .createMock();
        endpoint.signature = this.signature;
        expect(endpoint.generateNonce()).andReturn("nonce");
        expect(endpoint.signature.signRequest(isA(String.class), isA(String.class), isA(String.class)))
                .andThrow(new SignatureException());
        replay(endpoint, signature);
        endpoint.signedFetch("GET", "test/user");
    }

    @Test
    public void testSignedFetchValidDataValidResponse() throws ParseException, SignatureException, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch {
        AbstractEndpoint endpoint = EasyMock.createMockBuilder(AbstractEndpoint.class).addMockedMethod("request")
                .addMockedMethod("generateNonce").addMockedMethod("fetch", String.class, String.class, String.class)
                .createMock();
        JSONParser parser = new JSONParser();
        endpoint.signature = this.signature;
        JSONObject json = (JSONObject) parser
                .parse("{\"expires\":1455540581,\"status\":true," + "\"token\":\"token\", \"nonce\": \"nonce\"}");
        expect(endpoint.generateNonce()).andReturn("nonce");
        expect(endpoint.signature.signRequest(isA(String.class), isA(String.class), isA(String.class)))
                .andReturn("signature");
        expect(endpoint.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(endpoint, signature);
        assertEquals(json, endpoint.signedFetch("GET", "test/user", "filter=kba"));
    }

    @Test
    public void testSignedFetchNullResponse() throws SignatureException, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, SignatureFailed, NonceMismatch {
        AbstractEndpoint endpoint = EasyMock.createMockBuilder(AbstractEndpoint.class).addMockedMethod("request")
                .addMockedMethod("generateNonce").addMockedMethod("fetch", String.class, String.class, String.class)
                .createMock();
        endpoint.signature = this.signature;
        expect(endpoint.generateNonce()).andReturn("nonce");
        expect(endpoint.signature.signRequest(isA(String.class), isA(String.class), isA(String.class)))
                .andReturn("signature");
        expect(endpoint.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(null);
        replay(endpoint, signature);
        assertNull(endpoint.signedFetch("GET", "test/user", ""));
    }

    @Test(expected = NonceMismatch.class)
    public void testSignedfetchNonceMismatch() throws ParseException, SignatureException, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, SignatureFailed, NonceMismatch {
        AbstractEndpoint endpoint = EasyMock.createMockBuilder(AbstractEndpoint.class).addMockedMethod("request")
                .addMockedMethod("generateNonce").addMockedMethod("fetch", String.class, String.class, String.class)
                .createMock();
        JSONParser parser = new JSONParser();
        endpoint.signature = this.signature;
        JSONObject json = (JSONObject) parser
                .parse("{\"expires\":1455540581,\"status\":true," + "\"token\":\"token\", \"nonce\": \"nonce1\"}");
        expect(endpoint.generateNonce()).andReturn("nonce");
        expect(endpoint.signature.signRequest(isA(String.class), isA(String.class), isA(String.class)))
                .andReturn("signature");
        expect(endpoint.fetch(isA(String.class), isA(String.class), isA(String.class))).andReturn(json);
        replay(endpoint, signature);
        endpoint.signedFetch("GET", "test/user", "");
    }

    @Test
    public void testRequestValidResponse() throws RequestFailed, IOException, Exception {
        String method = "GET";
        String resource = "http://api.veridu/flavio/session/write";
        AbstractEndpoint endpoint = EasyMock.createMockBuilder(AbstractEndpoint.class).createMock();
        URL requestUrl = PowerMock.createMock(URL.class);
        HttpURLConnection conn = EasyMock.createMockBuilder(HttpURLConnection.class).addMockedMethod("getResponseCode")
                .addMockedMethod("getInputStream").createNiceMock();
        InputStream is = PowerMock.createMock(InputStream.class);
        InputStreamReader isr = EasyMock.createMock(InputStreamReader.class);
        BufferedReader rd = PowerMock.createNiceMock(BufferedReader.class);
        PowerMock.expectNew(URL.class, resource).andReturn(requestUrl).anyTimes();
        expect(requestUrl.openConnection()).andReturn(conn);
        PowerMock.expectNew(InputStreamReader.class, is).andReturn(isr);
        PowerMock.expectNew(BufferedReader.class, isr).andReturn(rd);
        expect(conn.getResponseCode()).andReturn(200);
        expect(conn.getInputStream()).andReturn(is);
        expect(rd.readLine()).andReturn("response").once();
        expect(rd.readLine()).andReturn(null).once();
        endpoint.storage = AbstractEndpointTest.storage;
        PowerMock.replay(URL.class, requestUrl);
        PowerMock.replay(HttpURLConnection.class, conn);
        PowerMock.replay(InputStream.class, is);
        PowerMock.replay(InputStreamReader.class, isr);
        PowerMock.replay(BufferedReader.class, rd);
        assertEquals("response\r", endpoint.request(method, resource, ""));
    }

    @Test
    public void testRequestValidData() throws RequestFailed, IOException, Exception {
        String method = "GET";
        String resource = "http://api.veridu/flavio/session/write";
        AbstractEndpoint endpoint = EasyMock.createMockBuilder(AbstractEndpoint.class).createMock();
        URL requestUrl = PowerMock.createMock(URL.class);
        HttpURLConnection conn = EasyMock.createMockBuilder(HttpURLConnection.class).addMockedMethod("getResponseCode")
                .addMockedMethod("getInputStream").createNiceMock();
        InputStream is = PowerMock.createMock(InputStream.class);
        InputStreamReader isr = EasyMock.createMock(InputStreamReader.class);
        BufferedReader rd = PowerMock.createNiceMock(BufferedReader.class);
        PowerMock.expectNew(URL.class, resource + "?filter=kba").andReturn(requestUrl).anyTimes();
        expect(requestUrl.openConnection()).andReturn(conn);
        PowerMock.expectNew(InputStreamReader.class, is).andReturn(isr);
        PowerMock.expectNew(BufferedReader.class, isr).andReturn(rd);
        expect(conn.getResponseCode()).andReturn(200);
        expect(conn.getInputStream()).andReturn(is);
        expect(rd.readLine()).andReturn("response").once();
        expect(rd.readLine()).andReturn(null).once();
        endpoint.storage = AbstractEndpointTest.storage;
        PowerMock.replay(URL.class, requestUrl);
        PowerMock.replay(HttpURLConnection.class, conn);
        PowerMock.replay(InputStream.class, is);
        PowerMock.replay(InputStreamReader.class, isr);
        PowerMock.replay(BufferedReader.class, rd);
        assertEquals("response\r", endpoint.request(method, resource, "filter=kba"));
    }

    @Test
    public void testRequestPostMethodValidData() throws RequestFailed, IOException, Exception {
        String method = "POST";
        String resource = "http://api.veridu/flavio/session/write";
        AbstractEndpoint endpoint = EasyMock.createMockBuilder(AbstractEndpoint.class).createMock();
        URL requestUrl = PowerMock.createMock(URL.class);
        HttpURLConnection conn = EasyMock.createMockBuilder(HttpURLConnection.class).addMockedMethod("getResponseCode")
                .addMockedMethod("getInputStream").addMockedMethod("getOutputStream").createNiceMock();
        InputStream is = PowerMock.createMock(InputStream.class);
        OutputStream os = PowerMock.createMock(OutputStream.class);
        InputStreamReader isr = EasyMock.createMock(InputStreamReader.class);
        BufferedReader rd = PowerMock.createNiceMock(BufferedReader.class);
        PowerMock.expectNew(URL.class, resource).andReturn(requestUrl).anyTimes();
        expect(requestUrl.openConnection()).andReturn(conn);
        PowerMock.expectNew(InputStreamReader.class, is).andReturn(isr);
        PowerMock.expectNew(BufferedReader.class, isr).andReturn(rd);
        expect(conn.getResponseCode()).andReturn(200);
        expect(conn.getInputStream()).andReturn(is);
        expect(conn.getOutputStream()).andReturn(os);
        expect(rd.readLine()).andReturn("response").once();
        expect(rd.readLine()).andReturn(null).once();
        endpoint.storage = AbstractEndpointTest.storage;
        PowerMock.replay(URL.class, requestUrl);
        PowerMock.replay(HttpURLConnection.class, conn);
        PowerMock.replay(InputStream.class, is);
        PowerMock.replay(InputStreamReader.class, isr);
        PowerMock.replay(BufferedReader.class, rd);
        assertEquals("response\r", endpoint.request(method, resource, "filter=kba"));
    }

    @Test
    public void testRequestWithValue() throws RequestFailed, IOException, Exception {
        String method = "GET";
        String resource = "http://api.veridu/flavio/mail/?flavio@flavio.com";
        AbstractEndpoint endpoint = EasyMock.createMockBuilder(AbstractEndpoint.class).createMock();
        URL requestUrl = PowerMock.createMock(URL.class);
        HttpURLConnection conn = EasyMock.createMockBuilder(HttpURLConnection.class).addMockedMethod("getResponseCode")
                .addMockedMethod("getInputStream").createNiceMock();
        InputStream is = PowerMock.createMock(InputStream.class);
        InputStreamReader isr = EasyMock.createMock(InputStreamReader.class);
        BufferedReader rd = PowerMock.createNiceMock(BufferedReader.class);
        PowerMock.expectNew(URL.class, resource).andReturn(requestUrl).anyTimes();
        expect(requestUrl.openConnection()).andReturn(conn);
        PowerMock.expectNew(InputStreamReader.class, is).andReturn(isr);
        PowerMock.expectNew(BufferedReader.class, isr).andReturn(rd);
        expect(conn.getResponseCode()).andReturn(200);
        expect(conn.getInputStream()).andReturn(is);
        expect(rd.readLine()).andReturn("response").once();
        expect(rd.readLine()).andReturn(null).once();
        endpoint.storage = AbstractEndpointTest.storage;
        PowerMock.replay(URL.class, requestUrl);
        PowerMock.replay(HttpURLConnection.class, conn);
        PowerMock.replay(InputStream.class, is);
        PowerMock.replay(InputStreamReader.class, isr);
        PowerMock.replay(BufferedReader.class, rd);
        assertEquals("response\r", endpoint.request(method, resource, ""));
    }

    @Test(expected = RequestFailed.class)
    public void testRequestFailed() throws RequestFailed, IOException {
        AbstractEndpoint endpoint = EasyMock.createMockBuilder(AbstractEndpoint.class).createMock();
        String resource = "http://api.veridu.com/test/user";
        URL requestUrl = PowerMock.createMock(URL.class);
        HttpURLConnection connection = mock(HttpURLConnection.class);
        expect(requestUrl.openConnection()).andReturn(connection);
        endpoint.request("GET", resource, "");
    }

}
