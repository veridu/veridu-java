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
 * Personal Resource
 *
 * @see <a href="https://veridu.com/wiki/Personal_Resource"> Wiki/
 *      Personal_Resource </a>
 * @version 1.0
 */
public class Personal extends AbstractEndpoint {

    /**
     * Filters by state
     */
    final static String TYPE_STATE = "state";
    /**
     * Filters by fields
     */
    final static String TYPE_FIELDS = "fields";
    /**
     * Filters by values
     */
    final static String TYPE_VALUES = "values";

    public Personal(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Creates one or more entries for the given user
     *
     * @param data
     *            Fields with names and values (See first:
     *            https://veridu.com/wiki/Personal_Resource#Fields_.26_Scoring)
     *
     * @return The fields in json format
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
     *      "https://veridu.com/wiki/Personal_Resource#How_to_create_one_or_more_entries_for_the_given_user">
     *      How to create one or more entries for the given user</a>
     */
    public JSONObject create(HashMap<String, String> data) throws EmptyUsername, EmptySession, InvalidUsername,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return create(data, this.storage.getUsername());
    }

    /**
     * Creates one or more entries for the given user
     *
     * @param data
     *            Fields with names and values (See first:
     *            https://veridu.com/wiki/Personal_Resource#Fields_.26_Scoring)
     * @param username
     *            String username
     *
     * @return The fields in json format
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
     *      "https://veridu.com/wiki/Personal_Resource#How_to_create_one_or_more_entries_for_the_given_user">
     *      How to create one or more entries for the given user</a>
     */
    public JSONObject create(HashMap<String, String> data, String username) throws EmptySession, InvalidUsername,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("POST", String.format("personal/%s", username), data);

        return json;
    }

    /**
     * Retrieves all form entries from a given user
     *
     * @return All form entries in json format
     *
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
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
     *      "https://veridu.com/wiki/Personal_Resource#How_to_retrieve_all_form_entries_from_a_given_user">
     *      How to retrieve all form entries from a given user</a>
     */
    public JSONObject retrieve() throws SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return retrieve(this.storage.getUsername());
    }

    /**
     * Retrieves all form entries from a given user
     *
     * @param username
     *            String username
     *
     * @return All form entries in json format
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
     *      "https://veridu.com/wiki/Personal_Resource#How_to_retrieve_all_form_entries_from_a_given_user">
     *      How to retrieve all form entries from a given user</a>
     */
    public JSONObject retrieve(String username) throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("personal/%s", username));

        return json;
    }

    /**
     * Retrieves all form entries from a given user
     *
     * @param type
     *            Filter, and it's value can be: state, fields and values
     * @param username
     *            String username
     *
     * @return All form entries filtered by type in json format
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
     * @throws EmptySession
     *             Exception
     * @throws EmptyUsername
     *             Exception
     * @throws InvalidUsername
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Personal_Resource#How_to_retrieve_all_form_entries_from_a_given_user">
     *      How to retrieve all form entries from a given user</a>
     */
    public JSONObject retrieve(String type, String username) throws SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, EmptySession, EmptyUsername, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("personal/%s/%s", username, type));

        return json;
    }

    /**
     * Updates one or more entries for the given user
     *
     * @param data
     *            Fields with names and values (See first:
     *            https://veridu.com/wiki/Personal_Resource#Fields_.26_Scoring)
     *
     * @return fields in a String format
     *
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
     * @throws InvalidUsername
     *             Exception
     * @throws EmptySession
     *             Exception
     * @throws UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Personal_Resource#How_to_update_one_or_more_entries_for_the_given_user">
     *      How to update one or more entries for the given user</a>
     */
    public String update(HashMap<String, String> data)
            throws EmptyUsername, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, EmptySession, InvalidUsername, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return update(data, this.storage.getUsername());
    }

    /**
     * Updates one or more entries for the given user
     *
     * @param data
     *            Fields with names and values (See first:
     *            https://veridu.com/wiki/Personal_Resource#Fields_.26_Scoring)
     * @param username
     *            String username
     *
     * @return fields in a String format
     *
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
     * @throws InvalidUsername
     *             Exception
     * @throws EmptySession
     *             Exception
     * @throws UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Personal_Resource#How_to_update_one_or_more_entries_for_the_given_user">
     *      How to update one or more entries for the given user</a>
     */
    public String update(HashMap<String, String> data, String username)
            throws EmptyUsername, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, InvalidUsername, EmptySession, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("PUT", String.format("personal/%s", username), data);

        return json.get("fields").toString();
    }

}
