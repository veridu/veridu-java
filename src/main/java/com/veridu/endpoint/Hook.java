package com.veridu.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
 * Hook Resource
 *
 * @see <a href="https://veridu.com/wiki/Hook_Resource"> Wiki/Hook_Resource </a>
 * @version 1.0
 */
public class Hook extends AbstractEndpoint {

    public Hook(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Creates a new Hook
     *
     * @param trigger
     *            One of the available hook (See:
     *            https://veridu.com/wiki/Hook_Resource#Available_Triggers)
     * @param url
     *            Full hook url
     *
     * @return Details of the hook in JSON format
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Hook_Resource#How_to_create_a_new_Hook">How
     *      to create a new Hook</a>
     */
    public JSONObject create(String trigger, String url) throws EmptySession, SignatureFailed, NonceMismatch,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        HashMap<String, String> data = new HashMap<>();
        data.put("trigger", trigger);
        data.put("url", url);

        JSONObject json = this.signedFetch("POST", "hook/", data);

        return json;
    }

    /**
     * Deletes a hook
     *
     * @param id
     *            the ID of the Hook
     *
     * @return boolean status
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Hook_Resource#How_to_delete_a_hook">How to
     *      delete a hook</a>
     */
    public boolean delete(int id) throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        JSONObject json = this.signedFetch("DELETE", String.format("hook/%s", id));

        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Retrieves detailed information about a hook
     *
     * @param id
     *            the ID of the Hook
     *
     * @return The details in JSON format
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Hook_Resource#How_to_retrieve_detailed_information_about_a_hook">
     *      How to retrieve detailed information about a hook</a>
     */
    public JSONObject details(int id) throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        JSONObject json = this.signedFetch("GET", String.format("hook/%s/", id));

        return (JSONObject) json.get("details");
    }

    /**
     * Retrieves a list of Hooks
     *
     * @return List of hooks in JSONArray format.
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Hook_Resource#How_to_retrieve_a_list_of_hooks">
     *      How to retrieve a list of hooks</a>
     */
    public JSONArray list() throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        JSONObject json = this.signedFetch("GET", "hook/");

        return (JSONArray) json.get("list");
    }
}
