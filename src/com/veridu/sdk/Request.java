package com.veridu.sdk;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Request {
    private String clientId;
    private String version = "0.3";
    private Signature signature;
    private String sessionToken = null;
    private int lastCode = 0;
    private String lastError = null;
    private static String sdkVersion = "0.1.2-beta";

    private String performRequest(String method, String url, String data) {
        HttpURLConnection connection = null;
        try {
            if ((method.compareTo("GET") == 0) && (data != null) && (!data.isEmpty())) {
                if (url.contains("?")) {
                    url = url.concat("&");
                } else {
                    url = url.concat("?");
                }
                url = url.concat(data);
            }
            //Create connection
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection)requestUrl.openConnection();
            connection.setRequestProperty("User-Agent", "Veridu-Java/".concat(sdkVersion));
            connection.setRequestProperty("Veridu-Client", this.clientId);
            if ((this.sessionToken != null) && (!this.sessionToken.isEmpty())) {
                connection.setRequestProperty("Veridu-Session", this.sessionToken);
            }
            connection.setRequestMethod(method);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            //Send request
            if ((method.compareTo("GET") != 0) && (data != null) && (!data.isEmpty())) {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));
                connection.setDoInput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();
            }

            //Get Response
            this.lastCode = connection.getResponseCode();
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            this.lastError = e.getMessage();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String generateNonce() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[10];
            random.nextBytes(bytes);
            return Hex.Encode(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    public Request(String clientId, String secret) {
        this.clientId = clientId;
        this.signature = new Signature(clientId, secret);
    }

    public JSONObject fetchSignedResource(String method, String resource) {
        try {
            String nonce = generateNonce();
            String sign = this.signature.signRequest(method, resource, nonce);
            JSONObject response = fetchResource(method, resource, sign);
            if (response == null)
                return null;
            if ((boolean) response.get("status")) {
                if (nonce.compareTo((String) response.get("nonce")) != 0) {
                    this.lastError = "Invalid nonce in response";
                    return null;
                }
                response.remove("nonce");
                return response;
            }
            return null;
        } catch (Exception ex) {
            this.lastError = ex.getMessage();
            return null;
        }
    }

    public JSONObject fetchResource(String method, String resource, String data) {
        try {
            String url = "https://api.veridu.com/" + this.version;
            if (resource.charAt(0) != '/') {
                url = url.concat("/");
            }
            url = url.concat(resource);
            String response = performRequest(method, url, data);
            if ((response == null) || (response.isEmpty())) {
                this.lastError = "Empty response from server";
                return null;
            }
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response);
            return json;
        } catch (Exception ex) {
            this.lastError = ex.getMessage();
            return null;
        }
    }

    public void setSessionToken(String token) {
        this.sessionToken = token;
    }

    public void setVersion(String version) {
        signature.setVersion(version);
        this.version = version;
    }

    public int lastCode() {
        return this.lastCode;
    }

    public String lastError() {
        return this.lastError;
    }

}
