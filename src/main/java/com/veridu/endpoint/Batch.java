package com.veridu.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.RequestFailed;
import com.veridu.storage.Storage;

/**
 * Batch Resource
 *
 * @see <a href="https://veridu.com/wiki/Batch_Resource">Wiki/Batch_Resource</a>
 * @version 1.0
 */
public class Batch extends AbstractEndpoint {

    public Batch(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Performs a batch request
     *
     * @param jobs
     *            List of jobs in a JSONObject format.
     *
     * @return response of each job in a JSONArray format
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
     * @throws UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Batch_Resource#How_to_perform_a_batch_request">
     *      How to perform a batch request</a>
     */
    public JSONArray request(List<JSONObject> jobs) throws EmptySession, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        HashMap<String, String> data = new HashMap<>();
        data.put("jobs", jobs.toString());

        JSONObject json = this.fetch("POST", "batch/", data);

        return (JSONArray) json.get("batch");
    }

}
