package edu.ttu.spm.DatabaseTestingRobot.request;

import edu.ttu.spm.DatabaseTestingRobot.Main;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static edu.ttu.spm.DatabaseTestingRobot.Main.CONNECTION_TIMEOUT;
import static edu.ttu.spm.DatabaseTestingRobot.Main.READ_TIMEOUT;

public class PostRequest {
    private final String TAG = "post json example";
    private String mEmail;
    public  String mPassword;

    PostRequest(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public void main(String[] args){
        String serverUrl = Main.BASE_URL + "/register";
        HashMap<String, String> postParams = new HashMap<>();

        postParams.put("username", mEmail);
        postParams.put("password", mPassword);

        URL url;
        String response = "";

        try {

            url = new URL(serverUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject root = new JSONObject();
            root.put("username", postParams.get("username"));
            root.put("password", postParams.get("password"));


            String str = root.toString();
            byte[] outputBytes = str.getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(outputBytes);

            int reponseCode = conn.getResponseCode();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            else {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
    } catch (JSONException e) {
            e.printStackTrace();
        }
    }}
