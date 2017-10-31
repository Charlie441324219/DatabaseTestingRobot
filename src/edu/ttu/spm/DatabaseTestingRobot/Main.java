package edu.ttu.spm.DatabaseTestingRobot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //set configuration
        Configuration configuration = new Configuration();
        configuration.setBaseURL("http://10.161.104.9:8080/cheapRide");
        configuration.setRequestTime(100);

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

        //draw graph
        reportGenerator.plot(postResult, configuration);
        reportGenerator.setLayout(null);
        JFrame frame = new JFrame();
        frame.setBackground(Color.white);

        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(reportGenerator);
        reportGenerator.updateUI();
        frame.setSize(800,800);
        frame.setVisible(true);
    }
}
