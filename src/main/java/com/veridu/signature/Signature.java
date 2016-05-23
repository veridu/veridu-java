package com.veridu.signature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class Signature
 */
public class Signature {
    private String clientid;
    private String secret;
    private String version = "0.3";

    public Signature(String clientid, String secret, String version) {
        this.clientid = clientid;
        this.secret = secret;
        setVersion(version);
    }

    private JSONObject readDefaultConfigFile() throws ParseException {
        InputStream is = this.getClass().getResourceAsStream("/config.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        StringBuilder configString = new StringBuilder();
        String line;
        try {
            line = bf.readLine();
            while (line != null) {
                configString.append(line);
                line = bf.readLine();
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(configString.toString());

        return json;
    }

    /**
     * Sets version for the API
     *
     * @param version
     *            String
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Signs the request
     *
     * @param method
     *            Method type
     * @param resource
     *            Resource String
     * @param nonce
     *            Nonce Hex * @return the request with signature
     *
     * @throws SignatureException
     *             Exception
     * @throws ParseException
     */

    public String signRequest(String method, String resource, String nonce) throws SignatureException, ParseException {
        try {
            JSONObject config = this.readDefaultConfigFile();
            String base_url = config.get("BASEURL").toString();
            String url = base_url + this.version;
            if (resource.charAt(0) != '/')
                url = url.concat("/");
            int queryPosition = resource.indexOf("?");
            if (queryPosition >= 0)
                resource = resource.substring(0, queryPosition);
            url = url.concat(resource);
            String data = "client=" + this.clientid + "&method=" + method + "&nonce=" + nonce + "&resource="
                    + URLEncoder.encode(url, "UTF-8") + "&timestamp="
                    + String.valueOf(System.currentTimeMillis() / 1000L) + "&version=" + this.version;
            String sign = HmacSHA1.Calculate(data, this.secret);
            return data.concat("&signature=").concat(URLEncoder.encode(sign, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Signature.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
