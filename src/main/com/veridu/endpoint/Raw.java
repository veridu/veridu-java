package veridu.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.json.simple.JSONObject;

import veridu.exceptions.APIError;
import veridu.exceptions.EmptyResponse;
import veridu.exceptions.EmptySession;
import veridu.exceptions.EmptyUsername;
import veridu.exceptions.InvalidFormat;
import veridu.exceptions.InvalidResponse;
import veridu.exceptions.InvalidUsername;
import veridu.exceptions.NonceMismatch;
import veridu.exceptions.RequestFailed;
import veridu.exceptions.SignatureFailed;
import veridu.storage.Storage;

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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Raw_Resource#How_to_retrieve_a_users_raw_profile_data">
     *      How to retrieve a users raw profile data</a>
     */
    public JSONObject retrieve() throws EmptySession, EmptyUsername, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Raw_Resource#How_to_retrieve_a_users_raw_profile_data">
     *      How to retrieve a users raw profile data</a>
     */
    public JSONObject retrieve(String type)
            throws EmptySession, EmptyUsername, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Raw_Resource#How_to_retrieve_a_users_raw_profile_data">
     *      How to retrieve a users raw profile data</a>
     */

    public JSONObject retrieve(String type, String username)
            throws EmptySession, InvalidUsername, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
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
