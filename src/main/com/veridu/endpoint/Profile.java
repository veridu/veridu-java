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
import veridu.exceptions.RequestFailed;
import veridu.storage.Storage;

/**
 * Profile Resource
 *
 * @see <a href="https://veridu.com/wiki/Profile_Resource"> Wiki/
 *      Profile_Resource </a>
 * @version 1.0
 */
public class Profile extends AbstractEndpoint {

    /**
     * Filters all
     */
    final static public int FILTER_ALL = 0xFFFF;
    /**
     * Filters by state
     */
    final static public int FILTER_STATE = 0x0001;
    /**
     * Filters by user
     */
    final static public int FILTER_USER = 0x0002;
    /**
     * Filters by details
     */
    final static public int FILTER_DETAILS = 0x0004;
    /**
     * Filters by document
     */
    final static public int FILTER_DOCUMENT = 0x0008;
    /**
     * Filters by badges
     */
    final static public int FILTER_BADGES = 0x0010;
    /**
     * Filters by certificate
     */
    final static public int FILTER_CERTIFICATE = 0x020;
    /**
     * Filters by flags
     */
    final static public int FILTER_FLAGS = 0x0040;
    /**
     * Filters by facts
     */
    final static public int FILTER_FACTS = 0x0080;
    /**
     * Filters by provider
     */
    final static public int FILTER_PROVIDER = 0x0100;
    /**
     * Filters by cpr
     */
    final static public int FILTER_CPR = 0x0200;
    /**
     * Filters by kba
     */
    final static public int FILTER_KBA = 0x0400;
    /**
     * Filters by user
     */
    final static public int FILTER_NEMID = 0x0800;
    /**
     * Filters by otp
     */
    final static public int FILTER_OTP = 0x1000;
    /**
     * Filters by personal
     */
    final static public int FILTER_PERSONAL = 0x2000;

    public Profile(String key, String version, String secret, Storage storage) {
        super(key, version, secret, storage);
    }

    /**
     * Creates filter name by filter map
     *
     * @param map
     *            Example: FILTER_KBA
     *
     * @return The filter's name
     */
    private String createFilter(int map) {
        String filter = null;
        if ((map & Profile.FILTER_STATE) == Profile.FILTER_STATE)
            filter = "state";

        if ((map & Profile.FILTER_USER) == Profile.FILTER_USER)
            if (filter == null)
                filter = "user";
            else
                filter += ", " + "user";

        if ((map & Profile.FILTER_DETAILS) == Profile.FILTER_DETAILS)
            if (filter == null)
                filter = "details";
            else
                filter += ", " + "details";

        if ((map & Profile.FILTER_DOCUMENT) == Profile.FILTER_DOCUMENT)
            if (filter == null)
                filter = "document";
            else
                filter += ", " + "document";

        if ((map & Profile.FILTER_BADGES) == Profile.FILTER_BADGES)
            if (filter == null)
                filter = "badges";
            else
                filter += ", " + "badges";

        if ((map & Profile.FILTER_CERTIFICATE) == Profile.FILTER_CERTIFICATE)
            if (filter == null)
                filter = "certificate";
            else
                filter += ", " + "certificate";

        if ((map & Profile.FILTER_FLAGS) == Profile.FILTER_FLAGS)
            if (filter == null)
                filter = "flags";
            else
                filter += ", " + "flags";
        if ((map & Profile.FILTER_FACTS) == Profile.FILTER_FACTS)
            if (filter == null)
                filter = "facts";
            else
                filter += ", " + "facts";

        if ((map & Profile.FILTER_PROVIDER) == Profile.FILTER_PROVIDER)
            if (filter == null)
                filter = "provider";
            else
                filter += ", " + "provider";
        if ((map & Profile.FILTER_CPR) == Profile.FILTER_CPR)
            if (filter == null)
                filter = "cpr";
            else
                filter += ", " + "cpr";

        if ((map & Profile.FILTER_KBA) == Profile.FILTER_KBA)
            if (filter == null)
                filter = "kba";
            else
                filter += ", " + "kba";

        if ((map & Profile.FILTER_NEMID) == Profile.FILTER_NEMID)
            if (filter == null)
                filter = "nemid";
            else
                filter += ", " + "nemid";
        if ((map & Profile.FILTER_OTP) == Profile.FILTER_OTP)
            if (filter == null)
                filter = "otp";
            else
                filter += ", " + "otp";

        if ((map & Profile.FILTER_PERSONAL) == Profile.FILTER_PERSONAL)
            if (filter == null)
                filter = "personal";
            else
                filter += ", " + "personal";

        return filter;
    }

    /**
     * Retrieves the consolidated profile of a given user
     *
     * @return The user's profile in json format
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
     *      "https://veridu.com/wiki/Profile_Resource#How_to_retrieve_the_consolidated_profile_of_a_given_user">
     *      How to retrieve the consolidated profile of a given user</a>
     */
    public JSONObject retrieve() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return retrieve(this.storage.getUsername());
    }

    /**
     * Retrieves the consolidated profile of a given user
     *
     * @param map
     *            Filter map
     *
     * @return The user's profile in json format
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
     *      "https://veridu.com/wiki/Profile_Resource#How_to_retrieve_the_consolidated_profile_of_a_given_user">
     *      How to retrieve the consolidated profile of a given user</a>
     */
    public JSONObject retrieve(int map) throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return retrieve(map, this.storage.getUsername());
    }

    /**
     * Retrieves the consolidated profile of a given user
     *
     * @param map
     *            Filter map
     * @param username
     *            String username
     *
     * @return The user's profile in json format
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
     *      "https://veridu.com/wiki/Profile_Resource#How_to_retrieve_the_consolidated_profile_of_a_given_user">
     *      How to retrieve the consolidated profile of a given user</a>
     */
    public JSONObject retrieve(int map, String username) throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        HashMap<String, String> data = new HashMap<>();
        data.put("filter", createFilter(map));

        JSONObject json = this.fetch("GET", String.format("profile/%s", username), data);

        return json;
    }

    /**
     * Retrieves the consolidated profile of a given user
     *
     * @param username
     *            String username
     *
     * @return The user's profile in json format
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
     *      "https://veridu.com/wiki/Profile_Resource#How_to_retrieve_the_consolidated_profile_of_a_given_user">
     *      How to retrieve the consolidated profile of a given user</a>
     */
    public JSONObject retrieve(String username) throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("profile/%s", username));

        return json;
    }
}
