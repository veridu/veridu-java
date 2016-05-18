package com.veridu.endpoint;

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
 * Session Resource
 *
 * @see <a href="https://veridu.com/wiki/Session_Resource"> Wiki/
 *      Session_Resource </a>
 * @version 1.0
 */
public class Session extends AbstractEndpoint {

    public Session(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Creates a limited lifetime session
     *
     * @param readonly
     *            If the session is going to be readonly or write (pass false
     *            value as parameter for the second option)
     *
     * @return true and creates the session
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Session_Resource#How_to_create_a_limited_lifetime_session_token">
     *      How to create a limited lifetime session token</a>
     */
    public boolean create(boolean readonly) throws SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        JSONObject json;
        if (readonly)
            json = this.signedFetch("POST", "session/read");
        else
            json = this.signedFetch("POST", "session/write");

        this.storage.setSessionToken(json.get("token").toString());
        this.storage.setSessionExpires(Integer.parseInt(json.get("expires").toString()));
        return true;
    }

    /**
     * Deletes the given session before it expires
     *
     * @return true and expires the session
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
     *      "https://veridu.com/wiki/Session_Resource#How_to_delete_the_given_session_token_before_it_expires">
     *      How to delete the given session token before it expires"</a>
     */
    public boolean expire() throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        this.signedFetch("DELETE", String.format("session/%s", this.storage.getSessionToken()));

        this.storage.purgeSession();
        return true;
    }

    /**
     * Extends the lifetime of a session
     *
     * @return true and extends the lifetime of the session
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
     *      "https://veridu.com/wiki/Session_Resource#How_to_extend_the_lifetime_of_a_session">
     *      How to extend the lifetime of a session</a>
     */
    public boolean extend() throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        JSONObject json = this.signedFetch("PUT", String.format("session/%s", this.storage.getSessionToken()));
        this.storage.setSessionExpires((long) json.get("expires"));
        return true;
    }

}
