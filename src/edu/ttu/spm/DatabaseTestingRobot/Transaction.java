package edu.ttu.spm.DatabaseTestingRobot;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

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

        long startTime = System.currentTimeMillis();

        StringEntity params =new StringEntity("{\"date\" : \"" + date + "\", \"provider\" : \"" + provider + "\", \"pickup\" : \"" + pickup + "\", \"destination\" : \"" + destination + "\", \"fee\" : \"" + fee + "\", \"username\" : \"" + username + "\"}");
        httpPost.setEntity(params);

        CloseableHttpResponse response = client.execute(httpPost);
        int responseCode = response.getStatusLine().getStatusCode();
        //System.out.println(responseCode);

        client.close();

        long elapsedTime = System.currentTimeMillis() - startTime;
        //System.out.println("Total elapsed http request/response time in milliseconds: " + elapsedTime);

        return new PostResult(responseCode,elapsedTime);
    }

    public void JsonGet(){

    }
}
