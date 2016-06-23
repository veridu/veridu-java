package com.veridu.endpoint;

import org.json.simple.JSONObject;

import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.EmptyUsername;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.InvalidUsername;
import com.veridu.exceptions.RequestFailed;
import com.veridu.storage.Storage;

/**
 * Details Resource
 *
 * @see <a href="https://veridu.com/wiki/Details_Resource"> Wiki/
 *      Details_Resource </a>
 * @version 1.0
 */
public class Details extends AbstractEndpoint {

    public Details(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Retrieves detailed info for a given user
     *
     * @return List of detailed info in JSONObject format.
     *
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
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
     * @throws EmptyUsername
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Details_Resource#How_to_retrieve_the_details_of_a_given_user">
     *      How to retrieve the details of a given user</a>
     */
    public JSONObject retrieve() throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return retrieve(this.storage.getUsername());
    }

    /**
     * Retrieves detailed info for a given user
     *
     * @param username
     *            String username
     *
     * @return List of detailed info in JSONObject format.
     *
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
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
     *      "https://veridu.com/wiki/Details_Resource#How_to_retrieve_the_details_of_a_given_user">
     *      How to retrieve the details of a given user</a>
     */
    public JSONObject retrieve(String username) throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("details/%s", username));

        return (JSONObject) json.get("list");
    }

}
