package com.veridu.endpoint;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.NonceMismatch;
import com.veridu.exceptions.RequestFailed;
import com.veridu.exceptions.SignatureFailed;
import com.veridu.signature.Hex;
import com.veridu.signature.Signature;
import com.veridu.storage.Storage;

/**
 * Class AbstractEndpoint implements behavior that is common to all endpoints
 *
 * @version 1.0
 */
public class AbstractEndpoint {

    /**
     * base API URL
     */
    final public static String BASE_URL = "https://api.veridu.com/";

    /**
     * Validates the username given
     *
     * @param username
     *            String
     *
     * @return Boolean
     */

    public static boolean validateUsername(String username) {
        CharSequence inputStr = username;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]*$");
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    /**
     * String client unique ID
     */
    private String key;
    /**
     * String Shared secret
     */
    private String secret;

    /**
     * API version to be used
     */
    private String version = "0.3";
    /**
     * Signature object for signed API requests
     */
    Signature signature;
    /**
     * Storage object for Session
     */
    Storage storage;

    /**
     * Last API response code
     */
    private int lastCode = 0;

    /**
     * Class constructor
     *
     * @param key
     *            Client Id
     * @param secret
     *            Client secret
     * @param version
     *            Version of API
     * @param storage
     *            Object that keeps session information
     */
    public AbstractEndpoint(String key, String secret, String version, Storage storage) {
        this.key = key;
        this.secret = secret;
        this.version = version;
        this.storage = storage;
        this.signature = new Signature(key, secret, version);
    }

    /**
     *
     * @param method
     *            String
     * @param resource
     *            String
     *
     * @return JSONObject API response
     *
     * @throws EmptyResponse
     *             Exception
     * @throws InvalidFormat
     *             Exception
     * @throws InvalidResponse
     *             Exception
     * @throws APIError
     *             Exception
     * @throws RequestFailed
     *             Exception
     */
    public JSONObject fetch(String method, String resource)
            throws EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed {
        return fetch(method, resource, "");
    }

    /**
     *
     * @param method
     *            String
     * @param resource
     *            String
     * @param data
     *            HashMap
     *
     * @return JSONObject API response
     *
     * @throws EmptyResponse
     *             Exception
     * @throws InvalidFormat
     *             Exception
     * @throws InvalidResponse
     *             Exception
     * @throws APIError
     *             Exception
     * @throws RequestFailed
     *             Exception
     * @throws UnsupportedEncodingException
     *             Exception
     */
    public JSONObject fetch(String method, String resource, HashMap<String, String> data) throws EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        String dataAsString = queryBuilder(data);
        return fetch(method, resource, dataAsString);
    }

    /**
     * Fetches an API Resource
     *
     * @param method
     *            String
     * @param resource
     *            String
     * @param data
     *            String
     *
     * @return JSONObject API response
     *
     * @throws EmptyResponse
     *             Exception
     * @throws InvalidFormat
     *             Exception
     * @throws InvalidResponse
     *             Exception
     * @throws APIError
     *             Exception
     * @throws RequestFailed
     *             Exception
     */
    public JSONObject fetch(String method, String resource, String data)
            throws EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed {
        JSONObject json;
        String url = AbstractEndpoint.BASE_URL + this.version;
        if (resource.charAt(0) != '/')
            url = url.concat("/");

        url = url.concat(resource);
        String response = request(method, url, data);

        if ((response == null) || (response.isEmpty()))
            throw new EmptyResponse();

        try {
            JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(response);
        } catch (ParseException ex) {
            throw new InvalidFormat();
        }

        if (!json.containsKey("status"))
            throw new InvalidResponse();

        if (json.get("status").equals(false)) {
            JSONObject error = (JSONObject) json.get("error");
            throw new APIError(error.get("message").toString());
        }

        return json;
    }

    String generateNonce() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[10];
            random.nextBytes(bytes);
            return Hex.Encode(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the Client Id
     *
     * @return key
     */
    public final String getKey() {
        return key;
    }

    /**
     * Gets the Secret
     *
     * @return secret
     */
    public final String getSecret() {
        return secret;
    }

    /**
     * Gets Version of API
     *
     * @return version
     */
    public final String getVersion() {
        return version;
    }

    /**
     * Method that converts Hash Table data to an encoded (UTF-8) String.
     *
     * @param data
     *
     * @return String dataAsString
     */
    private String queryBuilder(HashMap<String, String> data) throws UnsupportedEncodingException {
        if (data.isEmpty())
            return "";
        StringBuilder dataAsString = new StringBuilder();
        for (Entry<String, String> entry : data.entrySet()) {
            dataAsString.append("&");
            dataAsString.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            dataAsString.append("=");
            dataAsString.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return dataAsString.toString();
    }

    /**
     * Process request to API
     *
     * @param method
     *            String
     * @param url
     *            String
     * @param data
     *            String
     *
     * @return String response
     *
     * @throws RequestFailed
     *             Exception
     */
    public String request(String method, String url, String data) throws RequestFailed {
        HttpURLConnection connection = null;
        try {
            if ((method.compareTo("GET") == 0) && (data != null) && (!data.isEmpty())) {
                if (url.contains("?"))
                    url = url.concat("&");
                else
                    url = url.concat("?");
                url = url.concat(data);
            }
            // Create connection
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestProperty("Veridu-Client", this.key);
            if ((this.storage.getSessionToken() != null) && (!this.storage.getSessionToken().isEmpty()))
                connection.setRequestProperty("Veridu-Session", this.storage.getSessionToken());
            connection.setRequestMethod(method);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            // Send request
            if ((method.compareTo("GET") != 0) && (data != null) && (!data.isEmpty())) {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));
                connection.setDoInput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();
            }

            // Get Response
            this.lastCode = connection.getResponseCode();
            InputStream is;
            if (this.lastCode >= 400)
                is = connection.getErrorStream();
            else
                is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RequestFailed();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    /**
     * Sets the Client Id
     *
     * @param key
     *            String
     */
    public final void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets secret
     *
     * @param secret
     *            String
     */
    public final void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Sets Version of API
     *
     * @param version
     *            String
     */
    public final void setVersion(String version) {
        this.version = version;
    }

    /**
     *
     * @param method
     *            String
     * @param resource
     *            String
     *
     * @return JSONObject API response
     *
     * @throws SignatureFailed
     *             Exception
     * @throws NonceMismatch
     *             Exception
     * @throws EmptyResponse
     *             Exception
     * @throws InvalidFormat
     *             Exception
     * @throws InvalidResponse
     *             Exception
     * @throws APIError
     *             Exception
     * @throws RequestFailed
     *             Exception
     * @throws ParseException
     */
    public JSONObject signedFetch(String method, String resource) throws SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, ParseException {
        return signedFetch(method, resource, "");
    }

    /**
     *
     * @param method
     *            String
     * @param resource
     *            String
     * @param data
     *            HashMap
     *
     * @return JSONObject API response
     *
     * @throws SignatureFailed
     *             Exception
     * @throws NonceMismatch
     *             Exception
     * @throws EmptyResponse
     *             Exception
     * @throws InvalidFormat
     *             Exception
     * @throws InvalidResponse
     *             Exception
     * @throws APIError
     *             Exception
     * @throws RequestFailed
     *             Exception
     * @throws UnsupportedEncodingException
     *             Exception
     * @throws ParseException
     */
    public JSONObject signedFetch(String method, String resource, HashMap<String, String> data)
            throws SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, UnsupportedEncodingException, ParseException {
        String dataAsString = queryBuilder(data);
        return signedFetch(method, resource, dataAsString);
    }

    /**
     * Fetches an API Signed Resource
     *
     * @param method
     *            String
     * @param resource
     *            String
     * @param data
     *            String
     *
     * @return JSONObject API response
     *
     * @throws SignatureFailed
     *             Exception
     * @throws NonceMismatch
     *             Exception
     * @throws EmptyResponse
     *             Exception
     * @throws InvalidFormat
     *             Exception
     * @throws InvalidResponse
     *             Exception
     * @throws APIError
     *             Exception
     * @throws RequestFailed
     *             Exception
     * @throws ParseException
     *
     */
    public JSONObject signedFetch(String method, String resource, String data) throws SignatureFailed, NonceMismatch,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, ParseException {
        String nonce = generateNonce();
        String sign;
        JSONObject response;
        try {
            sign = this.signature.signRequest(method, resource, nonce);
        } catch (SignatureException ex) {
            throw new SignatureFailed();
        }

        if ((data == null) || (data.isEmpty()))
            response = fetch(method, resource, sign);
        else
            response = fetch(method, resource, sign.concat("&".concat(data)));

        if ((response == null) || (response.isEmpty()))
            return null;

        if (response.get("status").equals(true)) {
            if (nonce.compareTo(response.get("nonce").toString()) != 0)
                throw new NonceMismatch();
            response.remove("nonce");
            return response;
        }

        return null;
    }

}
