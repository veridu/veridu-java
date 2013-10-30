package com.veridu.sdk;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Session {
    private Request request;
    private String token = null;
    private Long expires = null;
    private String username = null;
    private String lastError = null;

    public Session(Request request) {
        this.request = request;
    }
    
    public boolean Create(boolean readonly) {
        try {
            String resource = (readonly ? "/session/" : "/session/write/");
            JSONObject response = this.request.fetchSignedResource("POST", resource);
            if (response == null) {
                this.lastError = this.request.lastError();
                return false;
            }
            if ((boolean) response.get("status")) {
                this.token = (String) response.get("token");
                this.expires = (Long) response.get("expires");
                this.request.setSessionToken(this.token);
                return true;
            }
            this.lastError = (String) ((JSONObject) response.get("error")).get("message");
            return false;
        } catch (Exception e) {
            this.lastError = e.getMessage();
            return false;
        }
    }
    
    public boolean Extend() {
        if ((this.token == null) || (this.token.isEmpty())) {
            this.lastError = "Cannot extend an empty session";
            return false;
        }
        try {
            String resource = "/session/".concat(this.token);
            JSONObject response = this.request.fetchSignedResource("PUT", resource);
            if (response == null) {
                this.lastError = this.request.lastError();
                return false;
            }
            if ((boolean) response.get("status")) {
                this.expires = (Long) response.get("expires");
                return true;
            }
            this.lastError = (String) ((JSONObject) response.get("error")).get("message");
            return false;
        } catch (Exception e) {
            this.lastError = e.getMessage();
            return false;
        }
    }
    
    public boolean Expire() {
        if ((this.token == null) || (this.token.isEmpty())) {
            this.lastError = "Cannot expire an empty session";
            return false;
        }
        try {
            String resource = "/session/".concat(this.token);
            JSONObject response = this.request.fetchSignedResource("DELETE", resource);
            if (response == null) {
                this.lastError = this.request.lastError();
                return false;
            }
            if ((boolean) response.get("status")) {
                this.request.setSessionToken(null);
                this.expires = null;
                this.token = null;
                this.username = null;
                return true;
            }
            this.lastError = (String) ((JSONObject) response.get("error")).get("message");
            return false;
        } catch (Exception e) {
            this.lastError = e.getMessage();
            return false;
        }
    }
    
    public boolean Assign(String username) {
        if ((this.token == null) || (this.token.isEmpty())) {
            this.lastError = "Cannot assign an user to an empty session";
            return false;
        }
        try {
            String resource = "/user/".concat(username);
            JSONObject response = this.request.fetchSignedResource("POST", resource);
            if (response == null) {
                this.lastError = this.request.lastError();
                return false;
            }
            if ((boolean) response.get("status")) {
                this.username = username;
                return true;
            }
            this.lastError = (String) ((JSONObject) response.get("error")).get("message");
            return false;
        } catch (Exception e) {
            this.lastError = e.getMessage();
            return false;
        }
    }
    
    public String getToken() {
        return this.token;
    }
    
    public Long getExpires() {
        return this.expires;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String lastError() {
        return this.lastError;
    }
}
