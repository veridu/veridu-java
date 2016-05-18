package com.veridu.endpoint;

import org.json.simple.JSONObject;

import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.EmptyUsername;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidProvider;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.InvalidUsername;
import com.veridu.exceptions.NonceMismatch;
import com.veridu.exceptions.RequestFailed;
import com.veridu.exceptions.SignatureFailed;
import com.veridu.storage.Storage;

/**
 * Check Resource
 *
 * @see <a href="https://veridu.com/wiki/Check_Resource"> Wiki/Check_Resource
 *      </a>
 * @version 1.0
 */
public class Check extends AbstractEndpoint {

    /**
     * filters none
     */
    public final static int NONE = 0x0000;
    /**
     * filters all tracesmarts
     */
    public final static int TRACESMART_ALL = 0xFFFF;
    /**
     * Provider: address
     */
    public final static int TRACESMART_ADDRESS = 0x0001;
    /**
     * Provider: dob tracesmart
     */
    public final static int TRACESMART_DOB = 0x0002;
    /**
     * Provider: driverlicense tracesmart
     */
    public final static int TRACESMART_DRIVERLICENSE = 0x0004;
    /**
     * Provider: passport tracesmart
     */
    public final static int TRACESMART_PASSPORT = 0x0008;
    /**
     * Provider: creditactive tracesmart
     */
    public final static int TRACESMART_CREDITACTIVE = 0x0010;
    /**
     * Filter detailed info for a given provider: all
     */
    public final static String FILTER_ALL = "all";
    /**
     * Filter detailed info for a given provider: state
     */
    public final static String FILTER_STATE = "state";
    /**
     * Filter detailed info for a given provider: info
     */
    public final static String FILTER_INFO = "info";

    public Check(String key, String version, String secret, Storage storage) {
        super(key, version, secret, storage);
    }

    /**
     * Creates a new background check
     *
     * @param provider
     *            (See:
     *            https://veridu.com/wiki/Check_Resource#Available_Providers)
     * @param map
     *            A valid Integer for setup parameter
     *
     * @return The ID of the task
     *
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
     *             Exception
     * @throws InvalidProvider
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
     *             Exception0
     * @throws RequestFailed
     *             Exception
     * @throws EmptyUsername
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Check_Resource#How_to_create_a_new_Background_Check">
     *      Create a new background check</a>
     */
    public String create(String provider, int map) throws EmptyUsername, EmptySession, InvalidUsername, InvalidProvider,
            SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return create(provider, map, this.storage.getUsername());
    }

    /**
     * Creates a new background check
     *
     * @param provider
     *            (See:
     *            https://veridu.com/wiki/Check_Resource#Available_Providers)
     * @param map
     *            A valid Integer for setup parameter
     * @param username
     *            String username
     *
     * @return The ID of the task
     *
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
     *             Exception
     * @throws InvalidProvider
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
     *             Exception0
     * @throws RequestFailed
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Check_Resource#How_to_create_a_new_Background_Check">
     *      Create a new background check</a>
     */
    public String create(String provider, int map, String username)
            throws EmptySession, InvalidUsername, InvalidProvider, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        String setup = null;

        switch (provider) {
        case "tracesmart":
            setup = this.tracesmartSetup(map);
            break;
        default:
            throw new InvalidProvider();
        }

        JSONObject json = this.signedFetch("POST", String.format("check/%s/%s", username, provider), "setup=" + setup);

        return json.get("task_id").toString();
    }

    /**
     * Retrieve data from a provider given
     *
     * @param provider
     *            (See:
     *            https://veridu.com/wiki/Check_Resource#Available_Providers)
     * @param map
     *            A valid Integer for setup parameter
     *
     * @return Detailed information about a given provider
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
     *      "https://veridu.com/wiki/Check_Resource#How_to_retrieve_data_from_one_provider">
     *      Retrieve data from one provider</a>
     */
    public JSONObject details(String provider, int map) throws EmptyUsername, EmptySession, SignatureFailed,
            NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return details(provider, map, this.storage.getUsername());
    }

    /**
     * Retrieve data from a provider given
     *
     * @param provider
     *            (See:
     *            https://veridu.com/wiki/Check_Resource#Available_Providers)
     * @param map
     *            A valid Integer for setup parameter
     * @param username
     *            String username
     *
     * @return Detailed information about a given provider
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
     *      "https://veridu.com/wiki/Check_Resource#How_to_retrieve_data_from_one_provider">
     *      Retrieve data from one provider</a>
     */
    public JSONObject details(String provider, int map, String username) throws EmptySession, SignatureFailed,
            NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();
        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        String setup = this.tracesmartSetup(map);
        JSONObject json = this.signedFetch("GET", String.format("check/%s/%s/?setup=%s/", username, provider, setup));

        return json;
    }

    /**
     * Retrieve data from a provider given
     *
     * @param provider
     *            (See:
     *            https://veridu.com/wiki/Check_Resource#Available_Providers)
     * @param map
     *            A valid Integer for setup parameter
     * @param username
     *            String username
     * @param filter
     *            Could be "state" or "info" *If you don't want to give a
     *            filter, just call: details(provider, map, username)*
     *
     * @return Detailed information about a given provider
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
     *      "https://veridu.com/wiki/Check_Resource#How_to_retrieve_data_from_one_provider">
     *      Retrieve data from one provider</a>
     */
    public JSONObject details(String provider, int map, String username, String filter)
            throws EmptySession, InvalidUsername, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        String setup = this.tracesmartSetup(map);
        JSONObject json = this.signedFetch("GET",
                String.format("check/%s/%s/?filter=%s&setup=%s", username, provider, filter, setup));

        return json;
    }

    /**
     * Retrieve data from all providers
     *
     * @return Data from all providers in a JSON format.
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
     *      "https://veridu.com/wiki/Check_Resource#How_to_retrieve_data_from_all_providers">
     *      Retrieve data from all providers</a>
     */
    public JSONObject listAll() throws EmptySession, EmptyUsername, SignatureFailed, NonceMismatch, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return listAll(this.storage.getUsername());
    }

    /**
     * Retrieve data from all providers
     *
     * @param username
     *            String username
     *
     * @return Data from all providers in a JSON format.
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
     *      "https://veridu.com/wiki/Check_Resource#How_to_retrieve_data_from_all_providers">
     *      Retrieve data from all providers</a>
     */
    public JSONObject listAll(String username) throws EmptySession, InvalidUsername, SignatureFailed, NonceMismatch,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.signedFetch("GET", String.format("check/%s", username));
        return json;
    }

    /**
     * Converts a map integer to a valid setup String
     *
     * @param map
     *            Integer
     *
     * @return Filter name(s)
     */
    private String tracesmartSetup(int map) {
        String filter = null;
        if ((map & Check.TRACESMART_ADDRESS) == Check.TRACESMART_ADDRESS)
            filter = "address";

        if ((map & Check.TRACESMART_DOB) == Check.TRACESMART_DOB)
            if (filter == null)
                filter = "dob";
            else
                filter += "," + "dob";

        if ((map & Check.TRACESMART_DRIVERLICENSE) == Check.TRACESMART_DRIVERLICENSE)
            if (filter == null)
                filter = "driving";
            else
                filter += "," + "driving";

        if ((map & Check.TRACESMART_PASSPORT) == Check.TRACESMART_PASSPORT)
            if (filter == null)
                filter = "passport";
            else
                filter += "," + "passport";

        if ((map & Check.TRACESMART_CREDITACTIVE) == Check.TRACESMART_CREDITACTIVE)
            if (filter == null)
                filter = "credit-active";
            else
                filter += "," + "credit-active";

        return filter;

    }
}
