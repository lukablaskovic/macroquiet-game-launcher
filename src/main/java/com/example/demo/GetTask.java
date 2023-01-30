package com.example.demo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetTask implements Runnable{
        private String endpoint;
        private Class dataType;
        public GetTask(String endpoint, Class dataType){
            this.endpoint = endpoint;
            this.dataType = dataType;
        }
        ArrayList<String> result = new ArrayList<>();
        //Fetch carousel images thread

        public void run() {
            try {
                APIConnector connector = new APIConnector();
                if(dataType == JSONObject.class){
                    JSONObject data = new JSONObject(connector.get(endpoint));
                    result.add(data.toString());
                }
                else if (dataType == JSONArray.class){
                    JSONArray data = new JSONArray(connector.get(endpoint));
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject object = data.getJSONObject(i);
                        result.add(object.getString("url"));
                    }
                }

            }catch(Exception e) {
                System.out.println("Error" + e.getMessage());
            }
        }
        public ArrayList<String> getResult(){return result;}

}
