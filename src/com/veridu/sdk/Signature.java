package com.veridu.sdk;

import com.veridu.sdk.HmacSHA1;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Signature {
    private String clientid;
    private String secret;
    private String version = "0.3";

    public Signature(String clientid, String secret) {
        this.clientid = clientid;
        this.secret = secret;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String signRequest(String method, String resource, String nonce) throws SignatureException {
        try {
            String url = "https://api.veridu.com/" + this.version + resource;
            String data = "client=" + this.clientid +
                "&method=" + method +
                "&nonce=" + nonce +
                "&resource=" + URLEncoder.encode(url, "UTF-8") +
                "&timestamp=" + String.valueOf(System.currentTimeMillis() / 1000L) +
                "&version=" + this.version;
            String sign = HmacSHA1.Calculate(data, this.secret);
            return data.concat("&signature=").concat(URLEncoder.encode(sign, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Signature.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
