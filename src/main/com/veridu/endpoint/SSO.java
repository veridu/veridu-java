package veridu.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.json.simple.JSONObject;

import veridu.exceptions.APIError;
import veridu.exceptions.EmptyResponse;
import veridu.exceptions.EmptySession;
import veridu.exceptions.InvalidFormat;
import veridu.exceptions.InvalidResponse;
import veridu.exceptions.NonceMismatch;
import veridu.exceptions.RequestFailed;
import veridu.exceptions.SignatureFailed;
import veridu.storage.Storage;

/**
 * SSO Resource
 *
 * @see <a href="https://veridu.com/wiki/SSO_Resource"> Wiki/SSO_Resource </a>
 * @version 1.0
 */
public class SSO extends AbstractEndpoint {

    public SSO(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Does a social single sign on
     *
     * @param provider
     *            Provider's name (Example: facebook)
     * @param data
     *            The data to be sent. The name as the hash key followed by the
     *            value as hash value.
     *
     * @return API response in json format
     *
     * @throws EmptySession
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
     * @throws UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/SSO_Resource#How_to_do_a_social_single_sign_on">
     *      How to do a social single sign on</a>
     */
    public JSONObject createOauth1(String provider, HashMap<String, String> data)
            throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        JSONObject json = this.signedFetch("POST", String.format("sso/%s", provider), data);

        return json;
    }

    /**
     * Does a social single sign on
     *
     * @param provider
     *            Provider's name (Example: facebook)
     * @param data
     *            The data to be sent. The name as the hash key followed by the
     *            value as hash value.
     *
     * @return API response in json format
     *
     * @throws EmptySession
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
     * @throws UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/SSO_Resource#How_to_do_a_social_single_sign_on">
     *      How to do a social single sign on</a>
     */
    public JSONObject createOauth2(String provider, HashMap<String, String> data)
            throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        JSONObject json = this.signedFetch("POST", String.format("sso/%s", provider), data);

        return json;
    }

}
