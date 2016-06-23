package com.veridu.endpoint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.EmptyUsername;
import com.veridu.exceptions.InvalidCertificate;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.InvalidUsername;
import com.veridu.exceptions.RequestFailed;
import com.veridu.storage.Storage;

/**
 * Certificate Resource
 *
 * @see <a href="https://veridu.com/wiki/Certificate_Resource">Wiki/
 *      Certificate_Resource</a>
 * @version 1.0
 */
public class Certificate extends AbstractEndpoint {

    public Certificate(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * List all certificates
     *
     * @return List of all certificates in JSONArray format
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
     *      "https://veridu.com/wiki/Certificate_Resource#How_to_retrieve_a_list_of_certificates_for_a_given_user">
     *      Wiki/List all certificates for the given user</a>
     */
    public JSONArray listAll() throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return listAll(this.storage.getUsername());
    }

    /**
     * List all certificates
     *
     * @param username
     *            String username
     *
     * @return List of all certificates in JSONArray format
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
     *      "https://veridu.com/wiki/Certificate_Resource#How_to_retrieve_a_list_of_certificates_for_a_given_user">
     *      Wiki/List all certificates for the given user</a>
     */
    public JSONArray listAll(String username) throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("certificate/%s", username));

        return (JSONArray) json.get("list");
    }

    /**
     * Retrieve a certificate information
     *
     * @param certificate
     *            Name of certificate (See:
     *            https://veridu.com/wiki/Certificate_Resource#
     *            Available_Certificates)
     *
     * @return Info of certificate given in JSONArray format.
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
     * @throws InvalidCertificate
     *             Exception
     * @throws InvalidUsername
     *             Exception
     * @throws EmptyUsername
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Certificate_Resource#How_to_retrieve_a_certificate_info_for_the_given_user">
     *      Certificate information for given user</a>
     */
    public JSONArray retrieve(String certificate) throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidCertificate, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return retrieve(certificate, this.storage.getUsername());
    }

    /**
     * Retrieve a certificate information
     *
     * @param certificate
     *            Name of certificate (See:
     *            https://veridu.com/wiki/Certificate_Resource#
     *            Available_Certificates)
     * @param username
     *            String username
     *
     * @return Info of certificate given in JSONArray format.
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
     * @throws InvalidCertificate
     *             Exception
     * @throws InvalidUsername
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Certificate_Resource#How_to_retrieve_a_certificate_info_for_the_given_user">
     *      Certificate information for given user</a>
     */
    public JSONArray retrieve(String certificate, String username) throws EmptySession, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidCertificate, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("certificate/%s/%s", username, certificate));

        return (JSONArray) json.get("certificate");

    }

}
