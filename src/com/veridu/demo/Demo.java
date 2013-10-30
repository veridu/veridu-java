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
        Session veriduSession = new Session(veriduRequest);
        if (!veriduSession.Create(false)) {
            System.out.println("Failed to create session!");
            System.out.printf("Error: %s\n", veriduSession.lastError());
            return;
        }
        if (!veriduSession.Assign("demo-user")) {
            System.out.println("Failed to create/assign user!");
            System.out.printf("Error: %s\n", veriduSession.lastError());
            return;
        }
        if (!veriduSession.Extend()) {
            System.out.println("Failed to extend session!");
            System.out.printf("Error: %s\n", veriduSession.lastError());
            return;
        }
        JSONObject userData = veriduRequest.fetchResource("GET", "/user/demo-user/", null);
        System.out.println(userData.toJSONString());
        JSONObject userProviders = veriduRequest.fetchResource("GET", "/provider/demo-user/", null);
        System.out.println(userProviders.toJSONString());
        JSONObject userKBA = veriduRequest.fetchResource("GET", "/kba/demo-user/", null);
        System.out.println(userKBA.toJSONString());
        JSONObject userOTP = veriduRequest.fetchResource("GET", "/otp/demo-user/", null);
        System.out.println(userOTP.toJSONString());
        if (!veriduSession.Expire()) {
            System.out.println("Failed to expire session!");
            System.out.printf("Error: %s\n", veriduSession.lastError());
        }
    }
}
