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
 * Backplane Resource
 *
 * @see <a href="https://veridu.com/wiki/Backplane_Resource"> Wiki/
 *      Backplane_Resource </a>
 * @version 1.0
 */
public class Backplane extends AbstractEndpoint {

    public Backplane(String key, String secret, String version, Storage storage) {
        super(key, secret, version, storage);
    }

    /**
     * Setup a client as a Backplane Profile
     *
     * @param channel
     *            Backplane provided communication channel
     *
     * @return Boolean status
     *
     * @throws EmptyUsername
     *             Exception
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
     *             exception()
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
     */
    public boolean setup(String channel) throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return setup(channel, this.storage.getUsername());
    }

    /**
     * Setup a client as a Backplane Profile
     *
     * @param channel
     *            Backplane provided communication channel
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
     * @throws UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/Backplane_Resource#How_to_setup_a_client_as_a_Backplane_Profile">
     *      Setup a client as a Backplane Profile </a>
     */
    public boolean setup(String channel, String username) throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("channel", channel);
        JSONObject json = this.fetch("POST", String.format("backplane/%s", username), data);

        return Boolean.parseBoolean(json.get("status").toString());
    }

}
