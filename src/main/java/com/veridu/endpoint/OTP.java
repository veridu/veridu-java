package com.veridu.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

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
 * OTP Resource
 *
 * @see <a href="https://veridu.com/wiki/OTP_Resource"> Wiki/OTP_Resource </a>
 * @version 1.0
 */
public class OTP extends AbstractEndpoint {

    public OTP(String key, String version, String secret, Storage storage) {
        super(key, version, secret, storage);
    }

    /**
     * Retrieves if the given user has verified the given value using Email
     *
     * @param email
     *            The email address
     *
     * @return Boolean status
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_retrieve_if_the_given_user_has_verified_the_given_value_using_the_given_OTP_method_name">
     *      How to retrieve if the given user has verified the given value using
     *      the given OTP method</a>
     */
    public boolean checkEmail(String email) throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return checkEmail(email, this.storage.getUsername());
    }

    /**
     * Retrieves if the given user has verified the given value using Email
     *
     * @param email
     *            The email address
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/OTP_Resource#How_to_retrieve_if_the_given_user_has_verified_the_given_value_using_the_given_OTP_method_name">
     *      How to retrieve if the given user has verified the given value using
     *      the given OTP method</a>
     */
    public boolean checkEmail(String email, String username) throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("otp/%s/email", username), email);

        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Retrieves if the given user has verified the given value using SMS
     *
     * @param phone
     *            The phone number
     *
     * @return Boolean status
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
     * @throws java.io.UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/OTP_Resource#How_to_retrieve_if_the_given_user_has_verified_the_given_value_using_the_given_OTP_method_name">
     *      How to retrieve if the given user has verified the given value using
     *      the given OTP method</a>
     */
    public boolean checkSMS(String phone) throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return checkSMS(phone, this.storage.getUsername());
    }

    /**
     * Retrieves if the given user has verified the given value using SMS
     *
     * @param phone
     *            The phone number
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
     * @throws java.io.UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/OTP_Resource#How_to_retrieve_if_the_given_user_has_verified_the_given_value_using_the_given_OTP_method_name">
     *      How to retrieve if the given user has verified the given value using
     *      the given OTP method</a>
     */
    public boolean checkSMS(String phone, String username) throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        HashMap<String, String> data = new HashMap<>();
        data.put("phone", phone);

        JSONObject json = this.fetch("GET", String.format("otp/%s/sms", username), data);

        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Creates an OTP Email under a given user
     *
     * @param email
     *            The email address
     * @param extended
     *            Boolean flag to extend email validation life span to 24h
     *            instead of 15 minutes
     * @param callbackURL
     *            Full validation url
     *
     * @return boolean status
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_create_an_OTP_method_under_a_given_user">
     *      How to create an OTP method under a given user</a>
     */
    public boolean createEmail(String email, boolean extended, String callbackURL)
            throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse, InvalidFormat, InvalidResponse,
            APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return createEmail(email, extended, callbackURL, this.storage.getUsername());
    }

    /**
     * Creates an OTP SMS under a given user
     *
     * @param email
     *            The email address
     * @param extended
     *            Boolean flag to extend email validation life span to 24h
     *            instead of 15 minutes
     * @param callbackURL
     *            Full validation url
     * @param username
     *            String username
     *
     * @return boolean status
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_create_an_OTP_method_under_a_given_user">
     *      How to create an OTP method under a given user</a>
     */
    public boolean createEmail(String email, boolean extended, String callbackURL, String username)
            throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat, InvalidResponse, APIError,
            RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        HashMap<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("extended", String.valueOf(extended));
        data.put("url", callbackURL);

        JSONObject json = this.fetch("POST", String.format("otp/%s/email", username), data);

        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Creates and OTP SMS under a given user
     *
     * @param phone
     *            The phone number
     *
     * @return boolean status
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
     * @throws EmptyUsername
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/OTP_Resource#How_to_create_an_OTP_method_under_a_given_user">
     *      How to create an OTP method under a given user</a>
     */
    public boolean createSMS(String phone) throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return createSMS(phone, this.storage.getUsername());
    }

    /**
     * Creates and OTP SMS under a given user
     *
     * @param phone
     *            The phone number
     * @param username
     *            String username
     *
     * @return boolean status
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_create_an_OTP_method_under_a_given_user">
     *      How to create an OTP method under a given user</a>
     */
    public boolean createSMS(String phone, String username) throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        HashMap<String, String> data = new HashMap<>();
        data.put("phone", phone);

        JSONObject json = this.fetch("POST", String.format("otp/%s/sms", username), data);

        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Retrieves a list of all OTP methods a give user has used to verify
     * himself
     *
     * @return list of all OTP in JSON array format
     *
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/OTP_Resource#How_to_retrieve_a_list_of_all_OTP_methods_a_give_user_has_used_to_verify_himself">
     *      How to retrieve a list of all OTP methods a give user has used to
     *      verify himself</a>
     */
    public JSONArray listAll() throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return listAll(this.storage.getUsername());
    }

    /**
     * Retrieves a list of all OTP methods a give user has used to verify
     * himself
     *
     * @param username
     *            String username
     *
     * @return list of all OTP in JSON array format
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_retrieve_a_list_of_all_OTP_methods_a_give_user_has_used_to_verify_himself">
     *      How to retrieve a list of all OTP methods a give user has used to
     *      verify himself</a>
     */
    public JSONArray listAll(String username) throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("otp/%s", username));

        return (JSONArray) json.get("list");
    }

    /**
     * Resends the email
     *
     * @param email
     *            The email address
     *
     * @return boolean status
     *
     * @throws EmptyResponse
     *             Exception
     * @throws EmptyUsername
     *             Exception
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_create_an_OTP_method_under_a_given_user">
     *      How to create an OTP method under a given user</a>
     */
    public boolean resendEmail(String email) throws EmptyUsername, EmptyResponse, EmptySession, InvalidUsername,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return resendEmail(email, this.storage.getUsername());
    }

    /**
     * Resends email
     *
     * @param email
     *            The email address
     * @param username
     *            String username
     *
     * @return Boolean status
     *
     * @throws EmptyResponse
     *             Exception
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/OTP_Resource#How_to_create_an_OTP_method_under_a_given_user">
     *      How to create an OTP method under a given user</a>
     */
    public boolean resendEmail(String email, String username) throws EmptyResponse, EmptySession, InvalidUsername,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        HashMap<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("resend", "true");

        JSONObject json = this.fetch("POST", String.format("otp/%s/email", username), data);
        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Resends the SMS
     *
     * @param phone
     *            The phone number
     *
     * @return Boolean status
     *
     * @throws EmptySession
     *             Exception
     * @throws InvalidUsername
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
     * @throws UnsupportedEncodingException
     *             Exception
     *
     * @see <a href=
     *      "https://veridu.com/wiki/OTP_Resource#How_to_create_an_OTP_method_under_a_given_user">
     *      How to create an OTP method under a given user</a>
     */
    public boolean resendSMS(String phone) throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return resendSMS(phone, this.storage.getUsername());
    }

    /**
     * Resends the SMS
     *
     * @param phone
     *            The phone number
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_create_an_OTP_method_under_a_given_user">
     *      How to create an OTP method under a given user</a>
     */
    public boolean resendSMS(String phone, String username) throws EmptySession, InvalidUsername, EmptyResponse,
            InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        HashMap<String, String> data = new HashMap<>();
        data.put("phone", phone);
        data.put("resend", "true");

        JSONObject json = this.fetch("POST", String.format("otp/%s/sms", username), data);

        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Check if the given user has verified with Email
     *
     * @return Boolean status
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_verify_the_OTP_method_under_a_given_user">
     *      How to verify the OTP method under a given user</a>
     */
    public boolean verifiedEmail() throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return verifiedEmail(this.storage.getUsername());
    }

    /**
     * Check if the given user has verified with Email
     *
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/OTP_Resource#How_to_verify_the_OTP_method_under_a_given_user">
     *      How to verify the OTP method under a given user</a>
     */
    public boolean verifiedEmail(String username) throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("otp/%s/email", username));

        return Boolean.parseBoolean(json.get("state").toString());
    }

    /**
     * Check if the given user has verified with SMS
     *
     * @return Boolean status
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_verify_the_OTP_method_under_a_given_user">
     *      How to verify the OTP method under a given user</a>
     */
    public boolean verifiedSMS() throws EmptyUsername, EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return verifiedSMS(this.storage.getUsername());
    }

    /**
     * Check if the given user has verified with SMS
     *
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
     *
     * @see <a href=
     *      "https://veridu.com/wiki/OTP_Resource#How_to_verify_the_OTP_method_under_a_given_user">
     *      How to verify the OTP method under a given user</a>
     */
    public boolean verifiedSMS(String username) throws EmptySession, InvalidUsername, EmptyResponse, InvalidFormat,
            InvalidResponse, APIError, RequestFailed {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        JSONObject json = this.fetch("GET", String.format("otp/%s/sms", username));

        return Boolean.parseBoolean(json.get("state").toString());
    }

    /**
     * Verify email under a given user
     *
     * @param email
     *            The email address
     * @param code
     *            The pin code
     *
     * @return Boolean status
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_verify_the_OTP_method_under_a_given_user">
     *      How to verify the OTP method under a given user</a>
     */
    public boolean verifyEmail(String email, String code) throws EmptyUsername, EmptySession, InvalidUsername,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return verifyEmail(email, code, this.storage.getUsername());
    }

    /**
     * Verify email under a given user
     *
     * @param email
     *            The email address
     * @param code
     *            The pin code
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_verify_the_OTP_method_under_a_given_user">
     *      How to verify the OTP method under a given user</a>
     */
    public boolean verifyEmail(String email, String code, String username) throws EmptySession, InvalidUsername,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        HashMap<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("code", code);

        JSONObject json = this.fetch("PUT", String.format("otp/%s/email", username), data);

        return Boolean.parseBoolean(json.get("status").toString());
    }

    /**
     * Verify SMS under a given user
     *
     * @param phone
     *            The phone number
     * @param code
     *            The pin code
     *
     * @return Boolean status
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_verify_the_OTP_method_under_a_given_user">
     *      How to verify the OTP method under a given user</a>
     * 
     */
    public boolean verifySMS(String phone, String code) throws EmptyUsername, EmptySession, InvalidUsername,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isUsernameEmpty())
            throw new EmptyUsername();

        return verifySMS(phone, code, this.storage.getUsername());
    }

    /**
     * Verify SMS under a given user
     *
     * @param phone
     *            The phone number
     * @param code
     *            The pin code
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
     *      "https://veridu.com/wiki/OTP_Resource#How_to_verify_the_OTP_method_under_a_given_user">
     *      How to verify the OTP method under a given user</a>
     */
    public boolean verifySMS(String phone, String code, String username) throws EmptySession, InvalidUsername,
            EmptyResponse, InvalidFormat, InvalidResponse, APIError, RequestFailed, UnsupportedEncodingException {
        if (this.storage.isSessionEmpty())
            throw new EmptySession();

        if (!AbstractEndpoint.validateUsername(username))
            throw new InvalidUsername();

        HashMap<String, String> data = new HashMap<>();
        data.put("phone", phone);
        data.put("code", code);

        JSONObject json = this.fetch("PUT", String.format("otp/%s/sms", username), data);

        return Boolean.parseBoolean(json.get("status").toString());
    }
}
