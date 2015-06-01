package com.veridu.demo;

import com.veridu.sdk.Request;
import com.veridu.sdk.Session;
import org.json.simple.JSONObject;

public class Demo {
    public static void main(String args[]) {
        if (args.length < 2) {
            System.out.println("Usage: demo clientid secret");
            return;
        }
        Request veriduRequest = new Request(args[0], args[1]);
        veriduRequest.setVersion("0.3");
        Session veriduSession = new Session(veriduRequest);
        if (!veriduSession.Create(false)) {
            System.err.println("Failed to create session!");
            System.err.printf("Error: %s\n", veriduSession.lastError());
            return;
        }
        if (!veriduSession.Assign("demo-user")) {
            System.err.println("Failed to create/assign user!");
            System.err.printf("Error: %s\n", veriduSession.lastError());
            return;
        }
        if (!veriduSession.Extend()) {
            System.err.println("Failed to extend session!");
            System.err.printf("Error: %s\n", veriduSession.lastError());
            return;
        }
        JSONObject userData = veriduRequest.fetchResource("GET", "/user/demo-user/", null);
        if (userData.isEmpty()) {
            System.err.println("Error: /user/demo-user returned an empty response!");
        } else {
            System.out.println(userData.toJSONString());
        }
        JSONObject userProviders = veriduRequest.fetchResource("GET", "/provider/demo-user/", null);
        if (userProviders.isEmpty()) {
            System.err.println("Error: /provider/demo-user returned an empty response!");
        } else {
            System.out.println(userProviders.toJSONString());
        }
        JSONObject userKBA = veriduRequest.fetchResource("GET", "/kba/demo-user/", null);
        if (userKBA.isEmpty()) {
            System.err.println("Error: /kba/demo-user returned an empty response!");
        } else {
            System.out.println(userKBA.toJSONString());
        }
        JSONObject userOTP = veriduRequest.fetchResource("GET", "/otp/demo-user/", null);
        if (userOTP.isEmpty()) {
            System.err.println("Error: /otp/demo-user returned an empty response!");
        } else {
            System.out.println(userOTP.toJSONString());
        }
        if (!veriduSession.Expire()) {
            System.err.println("Failed to expire session!");
            System.err.printf("Error: %s\n", veriduSession.lastError());
        }
    }
}
