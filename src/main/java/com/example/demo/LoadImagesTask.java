package com.example.demo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoadImagesTask implements Runnable{
        private String endpoint;
        public LoadImagesTask(String endpoint){
            this.endpoint = endpoint;
        }
        ArrayList<String> result = new ArrayList<>();
        //Fetch carousel images thread

        public void run() {
            try {
                APIConnector connector = new APIConnector();
                JSONArray data = connector.get(endpoint);

                for (int i = 0; i < data.length(); i++) {
                    JSONObject object = data.getJSONObject(i);
                    result.add(object.getString("url"));
                }
            }catch(Exception e) {
                System.out.println("Error" + e.getMessage());
            }
        }
        public ArrayList<String> getResult(){return result;}

}
