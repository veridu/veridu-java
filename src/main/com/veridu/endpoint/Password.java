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
 * Password Resource
 *
 * @see <a href="https://veridu.com/wiki/Password_Resource"> Wiki/
 *      Password_Resource </a>
 * @version 1.0
 */
public class Password extends AbstractEndpoint {

    public Password(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Creates a new user using SSO
     *
     * @param firstname
     *            The first name
     * @param lastname
     *            The last name
     * @param email
     *            The email address
     * @param password
     *            The password
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
     *      "https://veridu.com/wiki/Password_Resource#How_to_create_a_new_user_using_SSO">
     *      How to create a new user using SSO</a>
     */
    public JSONObject create(String firstname, String lastname, String email, String password)
            throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        HashMap<String, String> data = new HashMap<>();
        data.put("fname", firstname);
        data.put("lname", lastname);
        data.put("email", email);
        data.put("password", password);

        JSONObject json = this.signedFetch("POST", "password/signup", data);

        return json;
    }

    /**
     * Logins a user using SSO
     *
     * @param email
     *            The email address
     * @param password
     *            The password
     *
     * @return veridu_id in a String format
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
     *      "https://veridu.com/wiki/Password_Resource#How_to_login_a_user_using_SSO">
     *      How to login a user using SSO</a>
     */
    public String login(String email, String password) throws EmptySession, SignatureFailed, NonceMismatch,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        HashMap<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);

        JSONObject json = this.signedFetch("POST", "password/login", data);

        return json.get("veridu_id").toString();
    }

    /**
     * Recovers user's password first step
     *
     * @param email
     *            The email address
     * @param setupUrl
     *            full url where user should setup his new password
     *
     * @return Boolean status
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
     * @see <a href="https://veridu.com/wiki/Password_Resource#First_Step">
     *      Recover/First Step</a>
     */
    public boolean recoverFirstStep(String email, String setupUrl) throws EmptySession, SignatureFailed, NonceMismatch,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        HashMap<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("url", setupUrl);

        JSONObject json = this.signedFetch("POST", "password/recover", data);

        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Recovers user's password second step
     *
     * @param recover_hash
     *            reset hash provided by a POST to /password/recover (sent
     *            through e-mail straight to the user).
     * @param password
     *            The new Password
     *
     * @return Boolean status
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
     * @see <a href="https://veridu.com/wiki/Password_Resource#Second_Step">
     *      Recover/Second Step</a>
     */
    public boolean recoverSecondStep(String recover_hash, String password)
            throws EmptySession, SignatureFailed, NonceMismatch, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        HashMap<String, String> data = new HashMap<>();
        data.put("recover_hash", recover_hash);
        data.put("password", password);

        JSONObject json = this.signedFetch("PUT", "password", data);

        return Boolean.parseBoolean(json.get("status").toString());
    }
}
