package com.example.demo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginUserTask implements Runnable{
    private String endpoint;
    private JSONObject user_credentials;
    public LoginUserTask(String endpoint, JSONObject user_credentials){
        this.endpoint = endpoint;
        this.user_credentials = user_credentials;
    }
    JSONObject result = new JSONObject();

    public void run() {
        try {
            APIConnector connector = new APIConnector();
            JSONObject data = connector.post(endpoint, user_credentials);
            result = data;

        }catch(Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }
    public JSONObject getResult(){return result;}

}