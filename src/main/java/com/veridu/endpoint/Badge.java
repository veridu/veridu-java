package com.veridu.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.json.simple.JSONArray;
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
 * Badge Resource
 *
 * @see <a href="https://veridu.com/wiki/Badge_Resource"> Wiki/Badge_Resource
 *      </a>
 * @version 1.0
 */
public class Badge extends AbstractEndpoint {

    /**
     * "Credit-card" badge type
     */
    final static String BADGE_CREDITCARD = "credit-card";
    /**
     * "Telecom" badge type
     */
    final static String BADGE_TELECOM = "telecom";
    /**
     * "3rd-party-check" badge type
     */
    final static String BADGE_3RDPARTYCHECK = "3rdparty-check";
    /**
     * Filter: "state" type
     */
    final static String TYPE_STATE = "state";
    /**
     * Filter: "history" type
     */
    final static String TYPE_HISTORY = "history";
    /**
     * Filter: "all" type
     */
    final static String TYPE_ALL = "all";

    public Badge(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * @param badge
     *            Badge name (See:
     *            https://veridu.com/wiki/Badge_Resource#Available_Badges)
     * @param data
     *            Passing through Hash Table the name of the attribute as key,
     *            and the value as its value
     *
     * @return Boolean status
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
     * @throws EmptyUsername
     *             Exception
     * @throws ParseException
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Badge_Resource#How_to_assign_a_badge_to_a_given_user">
     *      Assign a badge to the given user</a>
     */
    public boolean create(String badge, HashMap<String, String> data)
            throws EmptyUsername, EmptySession, InvalidUsername, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException, ParseException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return create(badge, data, this.storage.getUsername());
    }

    /**
     * @param badge
     *            Badge name (See:
     *            https://veridu.com/wiki/Badge_Resource#Available_Badges)
     * @param data
     *            Passing through Hash Table the name of the attribute as key,
     *            and the value as its value
     * @param username
     *            String username
     *
     * @return Boolean status
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
     *      "https://veridu.com/wiki/Badge_Resource#How_to_assign_a_badge_to_a_given_user">
     *      Assign a badge to the given user</a>
     */
    public boolean create(String badge, HashMap<String, String> data, String username)
            throws EmptySession, InvalidUsername, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException, ParseException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        this.storage.setUsername(username);
        JSONObject json = this.signedFetch("POST", String.format("badge/%s/%s", username, badge), data);

        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Lists all Badges
     *
     * @return List of badges in JSONArray format
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
     *      "https://veridu.com/wiki/Badge_Resource#How_to_retrieve_a_list_of_badges_for_a_given_user">
     *      List of badges</a>
     */
    public JSONArray listAll() throws EmptySession, EmptyUsername, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return listAll(this.storage.getUsername());
    }

    /**
     * Lists all Badges
     *
     * @param username
     *            String username
     *
     * @return List of badges in JSONArray format
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
     *      "https://veridu.com/wiki/Badge_Resource#How_to_retrieve_a_list_of_badges_for_a_given_user">
     *      List of badges</a>
     */
    public JSONArray listAll(String username) throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("badge/%s", username));
        return (JSONArray) json.get("list");
    }

    /**
     * @param badge
     *            Badge name (See:
     *            https://veridu.com/wiki/Badge_Resource#Available_Badges
     * @param type
     *            Can be state, history, or all.
     *
     * @return JSONObject
     *
     * @throws EmptyUsername
     *             Exception
     * @throws InvalidUsername
     *             Exception
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Badge_Resource#How_to_retrieve_the_current_status_for_the_given_badge">
     *      Retrieve the current status for a given badge</a>
     *
     */
    public JSONObject retrieve(String badge, String type) throws EmptyUsername, InvalidUsername, EmptySession,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return retrieve(badge, type, this.storage.getUsername());
    }

    /**
     * Retrieve the current status for a given badge
     *
     * @param badge
     *            Badge name (See:
     *            https://veridu.com/wiki/Badge_Resource#Available_Badges)
     * @param type
     *            Can be state, history, or all.
     * @param username
     *            String username
     *
     * @return JSONObject
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
     *      "https://veridu.com/wiki/Badge_Resource#How_to_retrieve_the_current_status_for_the_given_badge">
     *      Retrieve the current status for a given badge</a>
     */
    public JSONObject retrieve(String badge, String type, String username) throws EmptySession, InvalidUsername,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("badge/%s/%s?type=%s", username, badge, type));
        return json;
    }

}
