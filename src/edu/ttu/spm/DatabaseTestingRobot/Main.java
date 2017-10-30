package edu.ttu.spm.DatabaseTestingRobot;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //set configuration
        Configuration configuration = new Configuration();
        configuration.setBaseURL("http://192.168.1.73:8080/cheapRide");
        configuration.setRequestTime(1000);

        //create a list to contain post results
        PostResult postResult[] = new PostResult[configuration.getRequestTime()];

        for(int i = 0; i < configuration.getRequestTime(); i++) {

            //set random values generator
            RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();

            //set format and variables
            JsonPostBody jsonPostBody = new JsonPostBody();
            jsonPostBody.setDate(randomValuesGenerator.getRandomDate());
            jsonPostBody.setProvider(randomValuesGenerator.getRandomProvider());
            jsonPostBody.setPickup(randomValuesGenerator.getRandomPickup());
            jsonPostBody.setDestination(randomValuesGenerator.getRandomDestination());
            jsonPostBody.setFee(randomValuesGenerator.getRandomFee());
            jsonPostBody.setUsername(randomValuesGenerator.getRandomUserName());

            //Json POST
            Transaction transaction = new Transaction();

            //save result
            postResult[i] =transaction.JsonPost(configuration, jsonPostBody);
        }

        //output report
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.outputReport(postResult, configuration);
    }
}
