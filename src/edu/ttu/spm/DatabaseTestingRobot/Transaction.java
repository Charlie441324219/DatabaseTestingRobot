package edu.ttu.spm.DatabaseTestingRobot;

//import net.sf.json.JSONObject;
//import net.sf.json.JSONSerializer;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Transaction {

    public PostResult JsonPost(Configuration configuration, JsonPostBody jsonPostBody) throws IOException {
        String postURL = configuration.getBaseURL() + "/setHistory";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(postURL);
        httpPost.addHeader("Content-Type", "application/json");
        Header e = httpPost.getFirstHeader("Content-Type");

        String date = jsonPostBody.getDate();
        String provider = jsonPostBody.getProvider();
        String pickup = jsonPostBody.getPickup();
        String destination = jsonPostBody.getDestination();
        String fee  = jsonPostBody.getFee();
        String username = jsonPostBody.getUsername();

        List<JsonPostBody> jsonPostBodies = new ArrayList<>();
        JsonPostBody temp = new JsonPostBody();

        temp.setPickup(pickup);
        temp.setProvider(provider);
        temp.setDestination(destination);
        temp.setFee(fee);
        temp.setDate(date);
        temp.setUsername(username);

        jsonPostBodies.add(temp);

        long startTime = System.currentTimeMillis();

        StringEntity params =new StringEntity("{\"date\" : \"" + date + "\", \"provider\" : \"" + provider + "\", \"pickup\" : \"" + pickup + "\", \"destination\" : \"" + destination + "\", \"fee\" : \"" + fee + "\", \"username\" : \"" + username + "\"}");
        httpPost.setEntity(params);

        CloseableHttpResponse response = client.execute(httpPost);
        int responseCode = response.getStatusLine().getStatusCode();
        //System.out.println(responseCode);

        client.close();

        long elapsedTime = System.currentTimeMillis() - startTime;
        //System.out.println("Total elapsed http request/response time in milliseconds: " + elapsedTime);

        return new PostResult(responseCode,elapsedTime,jsonPostBodies);
    }

    public GetResult JsonGet(Configuration configuration, JsonGetBody jsonGetBody) throws IOException, JSONException {

        String getURL = configuration.getBaseURL() + "/getHistoryByDate?";

        String username = jsonGetBody.getUsername() ;
        String fromDate = jsonGetBody.getFromDate();
        String toDate = jsonGetBody.getToDate();
        String pageNumber = jsonGetBody.getPageNumber();
        String size  = jsonGetBody.getSize();

        List<JsonPostBody> jsonGetBodies = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        String serverUrl = getURL + "username=" + username + "&from=" + fromDate + "&to=" + toDate + "&pageNumber=" + pageNumber + "&size=" + size;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(serverUrl);
        httpGet.addHeader("Content-Type", "application/json");
        Header e = httpGet.getFirstHeader("Content-Type");

        CloseableHttpResponse response = client.execute(httpGet);
        int responseCode = response.getStatusLine().getStatusCode();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));

        long elapsedTime = System.currentTimeMillis() - startTime;

        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            JSONArray ja = new JSONArray(inputLine);
            for(int i = 0; i < ja.length(); i++) {
                JsonPostBody temp = new JsonPostBody();
                JSONObject json = (JSONObject) ja.get(i);
                String usernameGet = json.getString("username");
                Long date = json.getLong("date");
                String SDate = getDate(date, "MM/dd/yyyy");
                String provider = json.getString("provider");
                String pickup = json.getString("pickup");
                String destination = json.getString("destination");
                String fee = json.getString("fee");

                temp.setDate(SDate);
                temp.setFee(fee);
                temp.setDestination(destination);
                temp.setProvider(provider);
                temp.setPickup(pickup);
                temp.setUsername(usernameGet);

                jsonGetBodies.add(temp);
            }
        }
        reader.close();

        return new GetResult(responseCode,elapsedTime,jsonGetBodies);
    }

    public String getDate(long milliSecond, String dateFormat){
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }
}
