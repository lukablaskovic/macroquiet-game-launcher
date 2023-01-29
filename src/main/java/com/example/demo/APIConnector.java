package com.example.demo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.json.JSONArray;

public class APIConnector {
    private static HttpURLConnection connection;
    private BufferedReader reader;
    private String line;
    private final StringBuffer responseContent = new StringBuffer();

    public JSONArray get(String endpoint) {

        try {
            URL url = new URL(endpoint);
            connection = (HttpURLConnection) url.openConnection();

            //Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            //If connection is unsuccessful.
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            //If connection is successful
            else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }

        JSONArray result = new JSONArray(responseContent.toString());
        return result;
    }
    public JSONObject post(String endpoint, JSONObject userCredentials){
        try{

            URL url = new URL(endpoint);
            connection = (HttpURLConnection) url.openConnection();

            //Request setup
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            byte[] postBytes = userCredentials.toString().getBytes("UTF-8");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(postBytes.length));
            connection.setDoOutput(true);
            connection.getOutputStream().write(postBytes);

            int status = connection.getResponseCode();

            //If connection is unsuccessful.
            if (status == 404 || status == 401) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            //If connection is successful
            else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        JSONObject result = new JSONObject(responseContent.toString());
        return result;
    }


}
