package veridu.endpoint;

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
 * Clone Resource
 *
 * @see <a href="https://veridu.com/wiki/Clone_Resource"> Wiki/Clone_Resource
 *      </a>
 * @version 1.0
 */
public class Clone extends AbstractEndpoint {

    public Clone(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Retrieves details about user's clones
     *
     * @return Details in JSON format
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
     * @throws EmptyUsername
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Clone_Resource#How_to_retrieve_details_about_users_clones">
     *      How to retrieve details about user's clones</a>
     */
    public JSONObject details() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, SignatureFailed, NonceMismatch, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return details(this.storage.getUsername());
    }

    /**
     * Retrieves details about user's clones
     *
     * @param username
     *            String username
     *
     * @return Details in JSON format
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Clone_Resource#How_to_retrieve_details_about_users_clones">
     *      How to retrieve details about user's clones</a>
     */
    public JSONObject details(String username) throws EmptySession, InvalidUsername, SignatureFailed, NonceMismatch,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.signedFetch("GET", String.format("clone/%s", username));

        return json;
    }

}
