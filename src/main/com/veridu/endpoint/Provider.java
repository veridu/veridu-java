package veridu.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

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
 * Provider Resource
 *
 * @see <a href="https://veridu.com/wiki/Provider_Resource"> Wiki/
 *      Provider_Resource </a>
 * @version 1.0
 */
public class Provider extends AbstractEndpoint {

    public Provider(String key, String version, String secret, Storage storage) {
        super(key, version, secret, storage);
    }

    /**
     * Checks if the given user has used the given provider as a verification
     * method
     *
     * @param provider
     *            Provider's name (Example: facebook)
     *
     * @return Boolean status
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
     *      "https://veridu.com/wiki/Provider_Resource#How_to_retrieve_if_the_given_user_has_used_the_given_provider_as_a_verification_method">
     *      How to retrieve if the given user has used the given provider as a
     *      verification method</a>
     */
    public boolean check(String provider) throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return check(provider, this.storage.getUsername());
    }

    /**
     * Checks if the given user has used the given provider as a verification
     * method
     *
     * @param provider
     *            Provider's name (Example: facebook)
     * @param username
     *            String username
     *
     * @return Boolean status
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
     *      "https://veridu.com/wiki/Provider_Resource#How_to_retrieve_if_the_given_user_has_used_the_given_provider_as_a_verification_method">
     *      How to retrieve if the given user has used the given provider as a
     *      verification method</a>
     */
    public boolean check(String provider, String username) throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("provider/%s/%s/state", username, provider));

        return Boolean.parseBoolean(json.get("state").toString());
    }

    /**
     * Creates access token under giver user with OAuth1
     *
     * @param provider
     *            Provider name (Example: facebook)
     * @param data
     *            The data that is going to be sent
     *
     * @return The task_id in String format
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
     * @throws UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Provider_Resource#How_to_create_a_access_token_under_given_user_for_the_given_provider">
     *      How to create access token under given user for the given
     *      provider</a>
     */
    public String createOAuth1(String provider, HashMap<String, String> data)
            throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return createOAuth1(provider, data, this.storage.getUsername());
    }

    /**
     * Creates access token under giver user with OAuth1
     *
     * @param provider
     *            Provider name (Example: facebook)
     * @param data
     *            The data that is going to be sent
     * @param username
     *            String username
     *
     * @return The task_id in String format
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
     * @throws UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Provider_Resource#How_to_create_a_access_token_under_given_user_for_the_given_provider">
     *      How to create access token under given user for the given
     *      provider</a>
     */
    public String createOAuth1(String provider, HashMap<String, String> data, String username)
            throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("POST", String.format("provider/%s/%s", username, provider), data);

        return json.get("task_id").toString();
    }

    /**
     * Creates access token under giver user with OAuth2
     *
     * @param provider
     *            Provider name (Example: facebook)
     * @param data
     *            The data that is going to be sent
     *
     * @return The task_id in String format
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
     * @throws UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Provider_Resource#How_to_create_a_access_token_under_given_user_for_the_given_provider">
     *      How to create access token under given user for the given
     *      provider</a>
     */
    public String createOAuth2(String provider, HashMap<String, String> data)
            throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return createOAuth2(provider, data, this.storage.getUsername());
    }

    /**
     * Creates access token under giver user with OAuth2
     *
     * @param provider
     *            Provider name (Example: facebook)
     * @param data
     *            The data that is going to be sent
     * @param username
     *            String username
     *
     * @return The task_id in String format
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
     * @throws UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Provider_Resource#How_to_create_a_access_token_under_given_user_for_the_given_provider">
     *      How to create access token under given user for the given
     *      provider</a>
     */
    public String createOAuth2(String provider, HashMap<String, String> data, String username)
            throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("POST", String.format("provider/%s/%s", this.storage.getUsername(), provider),
                data);

        return json.get("task_id").toString();
    }

    /**
     * Retrieves details of list of all providers a given user used to verify
     * himself
     *
     * @return All details in json format
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
     *      "https://veridu.com/wiki/Provider_Resource#How_to_retrieve_a_list_of_all_providers_a_given_user_used_to_verify_himself">
     *      how to retrieve a list of all providers a given user used to verify
     *      himself</a>
     */
    public JSONObject getAllDetails() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return getAllDetails(this.storage.getUsername());
    }

    /**
     * Retrieves details of list of all providers a given user used to verify
     * himself
     *
     * @param username
     *            String username
     *
     * @return All details in json format
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
     *      "https://veridu.com/wiki/Provider_Resource#How_to_retrieve_a_list_of_all_providers_a_given_user_used_to_verify_himself">
     *      how to retrieve a list of all providers a given user used to verify
     *      himself</a>
     */
    public JSONObject getAllDetails(String username) throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("provider/%s/all", username));

        return json;
    }

    /**
     * Lists all providers
     *
     * @return The list of providers in JSONArray format
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
     *      "https://veridu.com/wiki/Provider_Resource#How_to_retrieve_a_list_of_all_providers_a_given_user_used_to_verify_himself">
     *      how to retrieve a list of all providers a given user used to verify
     *      himself</a>
     */
    public JSONArray listAll() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return listAll(this.storage.getUsername());
    }

    /**
     * Lists all providers filtered by state
     *
     * @param username
     *            String username
     *
     * @return The list of providers in JSONArray format
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
     *      "https://veridu.com/wiki/Provider_Resource#How_to_retrieve_a_list_of_all_providers_a_given_user_used_to_verify_himself">
     *      how to retrieve a list of all providers a given user used to verify
     *      himself</a>
     */
    public JSONArray listAll(String username) throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("provider/%s/state", username));

        return (JSONArray) json.get("list");
    }
}
