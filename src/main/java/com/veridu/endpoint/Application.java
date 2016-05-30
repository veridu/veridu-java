package com.veridu.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.NonceMismatch;
import com.veridu.exceptions.RequestFailed;
import com.veridu.exceptions.SignatureFailed;
import com.veridu.storage.Storage;

/**
 * Application Resource
 *
 * @see <a href="https://veridu.com/wiki/Application_Resource"> Wiki/
 *      Application_Resource </a>
 * @version 1.0
 */
public class Application extends AbstractEndpoint {

    public Application(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Create a new Hosted Application
     *
     * @param provider
     *            Provider's name
     *
     * @return The application id
     *
     * @throws EmptySession
     *             Exception
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
     * @throws java.io.UnsupportedEncodingException
     *             Exception
     * @throws ParseException
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Application_Resource#How_to_create_a_new_hosted_application">
     *      Create new hosted application </a>
     */
    public int create(String provider, String key, String secret)
            throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnsupportedEncodingException, ParseException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        HashMap<String, String> data = new HashMap<>();
        data.put("key", key);
        data.put("secret", secret);
        data.put("provider", provider);

        JSONObject json = this.signedFetch("POST", "application/", data);

        return Integer.parseInt(json.get("appid").toString());
    }

    /**
     * Retrieve detailed information about a hosted application
     *
     * @param id
     *            Application ID
     *
     * @return Detailed information in JSONObject format
     *
     * @throws EmptySession
     *             Exception
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
     * @see <a href=
     *      "https://veridu.com/wiki/Application_Resource#How_to_get_a_detailed_information_about_a_hosted_application">
     *      How to get a detailed information about a hosted application </a>
     */
    public JSONObject details(int id) throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, ParseException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        JSONObject json = this.signedFetch("GET", String.format("application/%s", id));

        return json;
    }

    /**
     * List all Hosted Applications
     *
     * @return List of hosted applications in JSONArray format
     *
     * @throws EmptySession
     *             Exception
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
     * @see <a href=
     *      "https://veridu.com/wiki/Application_Resource#How_to_retrieve_a_list_of_all_hosted_applications">
     *      How to retrieve a list of all hosted applications </a>
     */
    public JSONArray listAll() throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, ParseException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        JSONObject json = this.signedFetch("GET", "application/");

        return (JSONArray) json.get("list");
    }

    /**
     * Disables or enables a hosted application
     *
     * @param id
     *            Application id
     * @param enabled
     *            Boolean option to disable or enable application
     *
     * @return Boolean status
     *
     * @throws EmptySession
     *             Exception
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Application_Resource#How_to_enable.2Fdisabled_a_hosted_application">
     *      How to enable/disable a host application</a>
     */
    public boolean setState(int id, boolean enabled) throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException, ParseException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        HashMap<String, String> data = new HashMap<>();
        data.put("enabled", String.valueOf(enabled));
        JSONObject json = this.signedFetch("GET", String.format("application/%s", id), data);

        return Boolean.parseBoolean(json.get("status").toString());
    }
}
