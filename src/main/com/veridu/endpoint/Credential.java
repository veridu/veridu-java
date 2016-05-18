package veridu.endpoint;

import org.json.simple.JSONObject;

import veridu.exceptions.APIError;
import veridu.exceptions.EmptyResponse;
import veridu.exceptions.EmptySession;
import veridu.exceptions.InvalidFormat;
import veridu.exceptions.InvalidResponse;
import veridu.exceptions.RequestFailed;
import veridu.storage.Storage;

/**
 * Credential Resource
 *
 * @see <a href="https://veridu.com/wiki/Credential_Resource"> Wiki/
 *      Credential_Resource </a>
 * @version 1.0
 */
public class Credential extends AbstractEndpoint {

    public Credential(String key, String version, String secret, Storage storage) {
        super(key, version, secret, storage);
    }

    /**
     * Retrieves general session information
     *
     * @return information in JSON format
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
     *
     * @see <a href="How_to_retrieve_general_session_information">How to
     *      retrieve general sesion information</a>
     */
    public JSONObject details()
            throws EmptySession, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        JSONObject json = this.fetch("GET", "credential");

        return json;
    }
}
