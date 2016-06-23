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
 * State Resource
 *
 * @see <a href="https://veridu.com/wiki/State_Resource"> Wiki/State_Resource
 *      </a>
 * @version 1.0
 */
public class State extends AbstractEndpoint {

    public State(String key, String version, String secret, Storage storage) {
        super(key, version, secret, storage);
    }

    /**
     * Retrieves the active verification state for the given user
     *
     * @return The state (See:
     *         https://veridu.com/wiki/State_Resource#Session_States)
     *
     * @throws InvalidUsername
     *             Exception
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/State_Resource#How_to_retrieve_the_active_verification_state_for_the_give_user">
     *      How to retrieve the active verification state for the given
     *      user"</a>
     */
    public String retrieve() throws EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, EmptySession, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return retrieve(this.storage.getUsername());
    }

    /**
     * Retrieves the active verification state for the given user
     *
     * @param username
     *            String username
     *
     * @return The state (See:
     *         https://veridu.com/wiki/State_Resource#Session_States)
     *
     * @throws InvalidUsername
     *             Exception
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/State_Resource#How_to_retrieve_the_active_verification_state_for_the_give_user">
     *      How to retrieve the active verification state for the given
     *      user"</a>
     */
    public String retrieve(String username) throws InvalidUsername, EmptySession, EmptyUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!State.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("state/%s", username));

        return json.get("state").toString();
    }
}
