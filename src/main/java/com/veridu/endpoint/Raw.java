package com.veridu.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.EmptyUsername;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.InvalidUsername;
import com.veridu.exceptions.NonceMismatch;
import com.veridu.exceptions.RequestFailed;
import com.veridu.exceptions.SignatureFailed;
import com.veridu.storage.Storage;

/**
 * Raw Resource
 *
 * @see <a href="https://veridu.com/wiki/Raw_Resource"> Wiki/Raw_Resource </a>
 * @version 1.0
 */
public class Raw extends AbstractEndpoint {

    /**
     * Available type: credential
     */
    final static String TYPE_CREDENTIAL = "credential";

    public Raw(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Retrieves a user's raw profile data
     *
     * @return User's raw profile data in json format
     *
     * @throws EmptySession
     *             Exception
     * @throws EmptyUsername
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
     *      "https://veridu.com/wiki/Raw_Resource#How_to_retrieve_a_users_raw_profile_data">
     *      How to retrieve a users raw profile data</a>
     */
    public JSONObject retrieve() throws EmptySession, EmptyUsername, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, ParseException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        JSONObject json = this.signedFetch("GET", String.format("raw/%s", this.storage.getUsername()));

        return (JSONObject) json.get("data");

    }

    /**
     * Retrieves a user's raw profile data
     *
     * @param type
     *            The type of provider. Available: credential
     *
     * @return User's raw profile data in json format
     *
     * @throws EmptySession
     *             Exception
     * @throws EmptyUsername
     *             Exception
     * @throws InvalidUsername
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
     *      "https://veridu.com/wiki/Raw_Resource#How_to_retrieve_a_users_raw_profile_data">
     *      How to retrieve a users raw profile data</a>
     */
    public JSONObject retrieve(String type)
            throws EmptySession, EmptyUsername, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException, ParseException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return retrieve(type, this.storage.getUsername());

    }

    /**
     * Retrieves a user's raw profile data
     *
     * @param type
     *            The type of provider. Available: credential
     * @param username
     *            String username
     *
     * @return User's raw profile data in json format
     *
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
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
     *      "https://veridu.com/wiki/Raw_Resource#How_to_retrieve_a_users_raw_profile_data">
     *      How to retrieve a users raw profile data</a>
     */

    public JSONObject retrieve(String type, String username)
            throws EmptySession, InvalidUsername, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException, ParseException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        HashMap<String, String> data = new HashMap<>();
        data.put("type", type);

        JSONObject json = this.signedFetch("GET", String.format("raw/%s", username), data);

        return (JSONObject) json.get(type);
    }

}
