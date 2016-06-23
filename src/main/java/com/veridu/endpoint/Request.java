package com.veridu.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.json.simple.JSONArray;
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
 * Request Resource
 *
 * @see <a href="https://veridu.com/wiki/Request_Resource"> Wiki/
 *      Request_Resource </a>
 * @version 1.0
 */
public class Request extends AbstractEndpoint {

    /**
     * Filters all
     */
    final static String FILTER_ALL = "all";
    /**
     * Filters by verification
     */
    final static String FILTER_VERIFICATION = "verification";

    public Request(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Creates a new request
     *
     * @param userFrom
     *            The username of who is requesting
     * @param userTo
     *            The username who is going to get the request
     * @param type
     *            The type of request. Can be all or verification.
     * @param message
     *            The custom message
     *
     * @return Boolean status
     *
     * @throws EmptySession
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
     *      "https://veridu.com/wiki/Request_Resource#How_to_create_a_new_request">
     *      How to create a new request</a>
     */
    public boolean create(String userFrom, String userTo, String type, String message) throws EmptySession,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        HashMap<String, String> data = new HashMap<>();
        data.put("message", message);

        JSONObject json = this.fetch("POST", String.format("request/%s/%s/%s", userFrom, userTo, type), data);

        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Retrieves a list of requests sent to the given user
     *
     * @param filter
     *            Can be verification or all
     * @param count
     *            Number of messages to retrieve (from 1 up to 10 messages)
     * @param maxId
     *            Max message id (default:null)
     *
     * @return The list of request in json format
     *
     * @throws EmptySession
     *             Exception
     * @throws EmptyUsername
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
     *      "https://veridu.com/wiki/Request_Resource#How_to_retrieve_the_listing_of_requests_sent_to_the_given_user">
     *      How to retrieve the listing of requests sent to the given user</a>
     */
    public JSONArray list(String filter, int count, String maxId) throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptyUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return list(filter, count, maxId, this.storage.getUsername());
    }

    /**
     * Retrieves a list of requests sent to the given user
     *
     * @param filter
     *            Can be verification or all
     * @param count
     *            Number of messages to retrieve (from 1 up to 10 messages)
     * @param maxId
     *            Max message id (default:null)
     * @param username
     *            String username
     *
     * @return The list of request in json format
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
     *      "https://veridu.com/wiki/Request_Resource#How_to_retrieve_the_listing_of_requests_sent_to_the_given_user">
     *      How to retrieve the listing of requests sent to the given user</a>
     */
    public JSONArray list(String filter, int count, String maxId, String username) throws EmptySession, InvalidUsername,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET",
                String.format("request/%s/%s/?count=%s&max_id=%s", username, filter, count, maxId));

        return (JSONArray) json.get("list");
    }

    /**
     * Retrieves the number of unread and total count of requests
     *
     * @return the number of unread and total count of requests in json format
     *
     * @throws EmptySession
     *             Exception
     * @throws EmptyUsername
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
     * @throws InvalidUsername
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Request_Resource#How_to_retrieve_the_number_of_unread_and_total_count_of_requests_sent_to_the_given_user">
     *      </a>
     */
    public JSONObject retrieve() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return retrieve(this.storage.getUsername());
    }

    /**
     * Retrieves the number of unread and total count of requests
     *
     * @param username
     *            String username
     *
     * @return the number of unread and total count of requests in json format
     *
     * @throws EmptySession
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
     * @throws InvalidUsername
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Request_Resource#How_to_retrieve_the_number_of_unread_and_total_count_of_requests_sent_to_the_given_user">
     *      </a>
     */
    public JSONObject retrieve(String username) throws EmptySession, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("request/%s", username));

        return json;
    }

}
