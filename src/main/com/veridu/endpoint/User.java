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
 * User Resource
 *
 * @see <a href="https://veridu.com/wiki/User_Resource"> Wiki/User_Resource </a>
 * @version 1.0
 */
public class User extends AbstractEndpoint {

    public User(String key, String version, String secret, Storage storage) {
        super(key, version, secret, storage);
    }

    /**
     * Retrieves details for the given attribute
     *
     * @param attribute
     *            Attributes name
     *
     * @return Attribute details in json format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_current_valuescore_field_for_the_given_attribute">
     *      how to retrieve the current value/score field for the given
     *      attribute</a>
     */
    public JSONObject attributeDetails(String attribute) throws EmptySession, EmptyUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return attributeDetails(attribute, this.storage.getUsername());
    }

    /**
     * Retrieves details for the given attribute
     *
     * @param attribute
     *            Attributes name
     * @param username
     *            String username
     *
     * @return Attribute details in json format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_current_valuescore_field_for_the_given_attribute">
     *      how to retrieve the current value/score field for the given
     *      attribute</a>
     */
    public JSONObject attributeDetails(String attribute, String username) throws EmptySession, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("user/%s/%s/all", username, attribute));

        return (JSONObject) json.get("attribute");
    }

    /**
     * Retrieves the score for the given attribute
     *
     * @param attribute
     *            The attribute name
     *
     * @return The score of the attribute given in String format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_current_valuescore_field_for_the_given_attribute">
     *      how to retrieve the current value/score field for the given
     *      attribute</a>
     */
    public String attributeScore(String attribute) throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return attributeScore(attribute, this.storage.getUsername());
    }

    /**
     * Retrieves the score for the given attribute
     *
     * @param attribute
     *            The attribute name
     * @param username
     *            String username
     *
     * @return The score of the attribute given in String format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_current_valuescore_field_for_the_given_attribute">
     *      how to retrieve the current value/score field for the given
     *      attribute</a>
     */
    public String attributeScore(String attribute, String username) throws EmptySession, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("user/%s/%s/score", username, attribute));

        return json.get("attribute").toString();
    }

    /**
     * Retrieves the value for the given attribute
     *
     * @param attribute
     *            The attribute name
     *
     * @return The value of the attribute given in String format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_current_valuescore_field_for_the_given_attribute">
     *      how to retrieve the current value/score field for the given
     *      attribute</a>
     */
    public String attributeValue(String attribute) throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return attributeValue(attribute, this.storage.getUsername());
    }

    /**
     * Retrieves the value for the given attribute
     *
     * @param attribute
     *            The attribute name
     * @param username
     *            String username
     *
     * @return The value of the attribute given in String format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_current_valuescore_field_for_the_given_attribute">
     *      how to retrieve the current value/score field for the given
     *      attribute</a>
     */
    public String attributeValue(String attribute, String username) throws EmptySession, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("user/%s/%s/value", username, attribute));

        return json.get("attribute").toString();
    }

    /**
     * Compares the value given with the value of the given attribute
     *
     * @param attribute
     *            The attribute's name
     * @param value
     *            The attribute's value
     *
     * @return The attribute in json format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_validation_score_when_comparing_the_given_value_with_the_inferred_value_for_the_given_attribute">
     *      how to retrieve the validation score when comparing the given value
     *      with the inferred value for the given attribute</a>
     */
    public JSONObject compareAttribute(String attribute, String value) throws EmptySession, EmptyUsername,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return compareAttribute(attribute, value, this.storage.getUsername());
    }

    /**
     * Compares the value given with the value of the given attribute
     *
     * @param attribute
     *            The attribute's name
     * @param value
     *            The attribute's value
     * @param username
     *            String username
     *
     * @return The attribute in json format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_validation_score_when_comparing_the_given_value_with_the_inferred_value_for_the_given_attribute">
     *      how to retrieve the validation score when comparing the given value
     *      with the inferred value for the given attribute</a>
     */
    public JSONObject compareAttribute(String attribute, String value, String username) throws EmptySession,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("user/%s/%s", username, attribute), value);

        return json;
    }

    /**
     * Creates a new user and assign it to the current session
     *
     * @param username
     *            String username
     *
     * @return Boolean status
     *
     * @throws RequestFailed
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
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
     *             Exception
     *
     *
     * @see <a href=
     *      "https://veridu.com/wiki/User_Resource#How_to_create_a_new_user_entry_and_assign_it_to_the_current_session">
     *      How to create a new user entry and assign it to the current
     *      session</a>
     */
    public boolean create(String username) throws RequestFailed, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, EmptySession, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        this.storage.setUsername(username);
        JSONObject json = this.signedFetch("POST", String.format("user/%s/", username));

        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Retrieves all attribute scores
     *
     * @return Profile scores in json format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_current_valuescore_field_for_the_given_attribute">
     *      How to retrieve the current value/score field for the given
     *      attribute</a>
     */
    public JSONObject getAllAttributeScores() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return getAllAttributeScores(this.storage.getUsername());
    }

    /**
     * Retrieves all attribute scores
     *
     * @param username
     *            String username
     *
     * @return Profile scores in json format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_current_valuescore_field_for_the_given_attribute">
     *      How to retrieve the current value/score field for the given
     *      attribute</a>
     */
    public JSONObject getAllAttributeScores(String username) throws EmptySession, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("user/%s/score", username));

        return (JSONObject) json.get("profile");
    }

    /**
     * Retrieves all attribute values
     *
     * @return Profile values in json format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_current_valuescore_field_for_the_given_attribute">
     *      How to retrieve the current value/score field for the given
     *      attribute</a>
     */
    public JSONObject getAllAttributeValues() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return getAllAttributeValues(this.storage.getUsername());
    }

    /**
     * Retrieves all attribute values
     *
     * @param username
     *            String username
     *
     * @return Profile values in json format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_current_valuescore_field_for_the_given_attribute">
     *      How to retrieve the current value/score field for the given
     *      attribute</a>
     */
    public JSONObject getAllAttributeValues(String username) throws EmptySession, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("user/%s/value", username));

        return (JSONObject) json.get("profile");
    }

    /**
     * Gets all details for the given user
     *
     * @return The profile in json format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_full_profile_and_scores_for_the_given_user">
     *      How to retrieve the full profile and scores for the given user</a>
     */
    public JSONObject getAllDetails() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return getAllDetails(this.storage.getUsername());
    }

    /**
     * Gets all details for the given user
     *
     * @param username
     *            String username
     *
     * @return The profile in json format
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
     *      "https://veridu.com/wiki/User_Resource#How_to_retrieve_the_full_profile_and_scores_for_the_given_user">
     *      How to retrieve the full profile and scores for the given user</a>
     */
    public JSONObject getAllDetails(String username) throws EmptySession, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("user/%s/all/", username));

        return (JSONObject) json.get("profile");
    }

    /**
     * Renames a user
     *
     * @param newUsername
     *            The new user's username
     *
     * @return boolean status
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/User_Resource#How_to_rename_a_user">How to
     *      rename a user</a>
     */
    public boolean rename(String newUsername) throws EmptySession, EmptyUsername, SignatureFailed, NonceMismatch,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return rename(newUsername, this.storage.getUsername());
    }

    /**
     * Renames a user
     *
     * @param newUsername
     *            The new user's username
     * @param username
     *            the old username
     *
     * @return boolean status
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
     *      "https://veridu.com/wiki/User_Resource#How_to_rename_a_user">How to
     *      rename a user</a>
     */
    public boolean rename(String newUsername, String username) throws EmptySession, InvalidUsername, SignatureFailed,
            NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.signedFetch("PUT", String.format("user/%s", username), "newusername=" + newUsername);

        return Boolean.parseBoolean(json.get("status").toString());
    }
}
