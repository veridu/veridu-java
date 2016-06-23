package com.veridu.endpoint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.RequestFailed;
import com.veridu.exceptions.UnavailableRegion;
import com.veridu.storage.Storage;

/**
 * Lookup Resource
 *
 * @see <a href="https://veridu.com/wiki/Lookup_Resource"> Wiki/Lookup_Resource
 *      </a>
 * @version 1.0
 */
public class Lookup extends AbstractEndpoint {

    public Lookup(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Retrieves info of known address
     *
     * @param region
     *            (See:https://veridu.com/wiki/Address_Lookup_Available_Regions)
     * @param id
     *            Region id
     *
     * @return Address info in JSON format
     *
     * @throws EmptySession
     *             Exception
     * @throws UnavailableRegion
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
     *      "https://veridu.com/wiki/Lookup_Resource#How_to_retrieve_the_full_information_of_a_known_address">
     *      How to retrieve full info of a known address</a>
     */
    public JSONObject retrieveAddressInfo(String region, int id) throws EmptySession, UnavailableRegion, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!region.equals("uk"))
            throw new UnavailableRegion();

        JSONObject json = this.fetch("GET", String.format("lookup/%s/%s", region, id));

        return (JSONObject) json.get("info");
    }

    /**
     * Retrieves Info of postcode
     *
     * @param region
     *            (See:
     *            https://veridu.com/wiki/Address_Lookup_Available_Regions)
     * @param postcode
     *            Lookup postcode for given region
     *
     * @return Address info in JSONArray format
     *
     * @throws EmptySession
     *             Exception
     * @throws UnavailableRegion
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
     *      "https://veridu.com/wiki/Lookup_Resource#How_to_retrieve_the_full_information_of_a_postcode">
     *      How to retrieve full info of postcode</a>
     */
    public JSONArray retrievePostCodeInfo(String region, String postcode) throws EmptySession, UnavailableRegion,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!region.equals("uk"))
            throw new UnavailableRegion();

        JSONObject json = this.fetch("GET", String.format("lookup/%s?postcode=%s", region, postcode));

        return (JSONArray) json.get("results");
    }

}
