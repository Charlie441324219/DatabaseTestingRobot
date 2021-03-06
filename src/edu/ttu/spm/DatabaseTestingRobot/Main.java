package edu.ttu.spm.DatabaseTestingRobot;

import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import static edu.ttu.spm.DatabaseTestingRobot.Main.REQUEST_NUM;
import static edu.ttu.spm.DatabaseTestingRobot.Main.URL;

public class Main {
    static int THREAD = 2;
    static String URL = "http://192.168.1.73:8080/cheapRide";
    //    static String URL = "http://10.161.95.97:8080/cheapRide";
    static int REQUEST_NUM = 40;

    public static void main(String[] args){

        for (int i = 0; i < THREAD; i++){
            Multithreading user = new Multithreading();
            user.start();
        }
    }
}

class Multithreading extends Thread {

    public void run() {

        //set configuration
        Configuration configuration = new Configuration();
        configuration.setBaseURL(URL);
        configuration.setRequestNumber(REQUEST_NUM);

        //create a array to contain post and get results
        PostResult postResult[] = new PostResult[REQUEST_NUM];
        GetResult getResult[] = new GetResult[REQUEST_NUM];
        //create a array to contain post information
        JsonPostBody[] jsonTemp = new JsonPostBody[REQUEST_NUM];

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
            jsonPostBody.setUsername(randomValuesGenerator.getRandomUserName(i));

            //save post information in array
            jsonTemp[i] = jsonPostBody;

            //Json POST
            Transaction transaction = new Transaction();

            //save result
            try {
                postResult[i] =transaction.JsonPost(configuration, jsonPostBody);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < configuration.getRequestTime(); i++) {
            //set format and variables
            JsonGetBody jsonGetBody = new JsonGetBody();
            jsonGetBody.setUsername(jsonTemp[i].getUsername());
            jsonGetBody.setFromDate("1/1/2000");
            jsonGetBody.setToDate("1/1/2021");
            jsonGetBody.setPageNumber("1");
            jsonGetBody.setSize("1");

            //Json GET
            Transaction transaction = new Transaction();

            //save result
            try {
                getResult[i] =transaction.JsonGet(configuration, jsonGetBody);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //output report
        ReportGenerator reportGenerator = new ReportGenerator();
        try {
            reportGenerator.outputReport(postResult, getResult,configuration);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //draw graph
        reportGenerator.plot(postResult, getResult, configuration);
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
