package com.veridu.endpoint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.veridu.exceptions.APIError;
import com.veridu.exceptions.EmptyResponse;
import com.veridu.exceptions.EmptySession;
import com.veridu.exceptions.EmptyUsername;
import com.veridu.exceptions.InvalidFormat;
import com.veridu.exceptions.InvalidResponse;
import com.veridu.exceptions.InvalidUsername;
import com.veridu.exceptions.RequestFailed;
import com.veridu.storage.Storage;

/**
 * Task Resource
 *
 * @see <a href="https://veridu.com/wiki/Task_Resource"> Wiki/Task_Resource </a>
 * @version 1.0
 */
public class Task extends AbstractEndpoint {

    public Task(String key, String version, String secret, Storage storage) {
        super(key, version, secret, storage);
    }

    /**
     * Retrieves information about a given task for the given user
     *
     * @param taskId
     *            The id of the task
     *
     * @return The info in json format
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
     *      "https://veridu.com/wiki/Task_Resource#HHow_to_retrieve_information_about_a_given_task_for_the_given_user">
     *      how to retrieve information about a given task for the given
     *      user</a>
     */
    public JSONObject details(int taskId) throws EmptyUsername, EmptySession, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return details(taskId, this.storage.getUsername());
    }

    /**
     * Retrieves information about a given task for the given user
     *
     * @param taskId
     *            The id of the task
     * @param username
     *            String username
     *
     * @return The info in json format
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
     *      "https://veridu.com/wiki/Task_Resource#HHow_to_retrieve_information_about_a_given_task_for_the_given_user">
     *      how to retrieve information about a given task for the given
     *      user</a>
     */
    public JSONObject details(int taskId, String username) throws EmptySession, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed, InvalidUsername {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("task/%s/%d", username, taskId));

        return (JSONObject) json.get("info");
    }

    /**
     * Retrieves the active task list for the given user
     *
     * @return The active task list in JSONArray format
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
     *      "https://veridu.com/wiki/Task_Resource#How_to_retrieve_the_active_task_list_for_the_given_user">
     *      How to retrieve the active task list for the given user</a>
     */
    public JSONArray listAll() throws EmptySession, EmptyUsername, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, InvalidUsername {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return listAll(this.storage.getUsername());

    }

    /**
     * Retrieves the active task list for the given user
     *
     * @param username
     *            String username
     *
     * @return The active task list in JSONArray format
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
     *      "https://veridu.com/wiki/Task_Resource#How_to_retrieve_the_active_task_list_for_the_given_user">
     *      How to retrieve the active task list for the given user</a>
     */
    public JSONArray listAll(String username) throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("task/%s", username));

        return (JSONArray) json.get("list");

    }
}
