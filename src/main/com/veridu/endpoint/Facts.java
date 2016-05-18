package veridu.endpoint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import veridu.exceptions.APIError;
import veridu.exceptions.EmptyResponse;
import veridu.exceptions.EmptySession;
import veridu.exceptions.EmptyUsername;
import veridu.exceptions.InvalidFormat;
import veridu.exceptions.InvalidResponse;
import veridu.exceptions.InvalidUsername;
import veridu.exceptions.RequestFailed;
import veridu.storage.Storage;

/**
 * Facts Resource
 *
 * @see <a href="https://veridu.com/wiki/Facts_Resource"> Wiki/Facts_Resource
 *      </a>
 * @version 1.0
 */
public class Facts extends AbstractEndpoint {

    /**
     * provider name : Facebook
     */
    final static String PROVIDER_FACEBOOK = "facebook";
    /**
     * provider name : Twitter
     */
    final static String PROVIDER_TWITTER = "twitter";
    /**
     * provider name : LinkedIn
     */
    final static String PROVIDER_LINKEDIN = "linkedin";
    /**
     * provider name : Google
     */
    final static String PROVIDER_GOOGLE = "google";
    /**
     * provider name : Paypal
     */
    final static String PROVIDER_PAYPAL = "paypal";

    public Facts(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Retrieves a full facts list of a provided user
     *
     * @return The list of facts in a JSONArray format
     *
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
     * @throws EmptyUsername
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Facts_Resource#How_to_retrieve_a_full_facts_list_of_a_provided_user">
     *      How to retrieve a full facts list</a>
     */
    public JSONArray listAll() throws EmptyUsername, InvalidUsername, EmptySession, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return listAll(this.storage.getUsername());
    }

    /**
     * Retrieves a full facts list of a provided user
     *
     * @param username
     *            String username
     *
     * @return The list of facts in a JSONArray format
     *
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
     *      "https://veridu.com/wiki/Facts_Resource#How_to_retrieve_a_full_facts_list_of_a_provided_user">
     *      How to retrieve a full facts list</a>
     */
    public JSONArray listAll(String username) throws InvalidUsername, EmptySession, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("facts/%s", username));

        return (JSONArray) json.get("list");
    }

    /**
     * Retrieves list of facts of a provided user for a specific provider
     *
     * @param provider
     *            Name of provider to list the facts for (See:
     *            https://veridu.com/wiki/List_of_Profile_Facts)
     *
     * @return The list of facts in JSON format
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
     */
    public JSONObject retrieve(String provider) throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return retrieve(provider, this.storage.getUsername());
    }

    /**
     * Retrieves list of facts of a provided user for a specific provider
     *
     * @param provider
     *            Name of provider to list the facts for (See:
     *            https://veridu.com/wiki/List_of_Profile_Facts)
     * @param username
     *            String username
     *
     * @return The list of facts in JSON format
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
     */
    public JSONObject retrieve(String provider, String username) throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("facts/%s/%s", username, provider));

        return (JSONObject) json.get("facts");
    }
}
