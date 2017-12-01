package edu.ttu.spm.DatabaseTestingRobot;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;

public class ReportGenerator extends JPanel {
    String xlabel, ylabel, title, sublabel1, sublabel2;
    int xdim, ydim, yzero, xzero, xdraw, ydraw;
    double xtic, ytic, xpoint, ypoint;
    double  xmax, xmin, ymax, ymin;
    int size;
    int[] xx;
//    int[] yy;
    int[] yyP;
    int[] yyG;
    int counterPost = 0;
    int counterGet = 0;
    int counterCorr = 0;
    long threadID = Thread.currentThread().getId();

    public void outputReport(PostResult[] arrayOfPostResult, GetResult[] arrayOfGetResult,Configuration configuration) throws FileNotFoundException {

        String fileName = "report.txt" + threadID;
        PrintWriter outputString = new PrintWriter(fileName);

        outputString.write("=================================================================REPORT================================================================= ");
        outputString.append("\n");
        outputString.append("Thread" + Thread.currentThread().getId());
        outputString.append("\n");

        for (Integer numberOfRow = 0; numberOfRow < configuration.getRequestTime(); numberOfRow ++) {
            if(arrayOfPostResult[numberOfRow].getStatusCode() == 200)
                counterPost ++;
            if(arrayOfGetResult[numberOfRow].getStatusCode() == 200)
                counterGet ++;
            if(Objects.equals(compare(arrayOfPostResult[numberOfRow], arrayOfGetResult[numberOfRow]), "passed"))
                counterCorr ++;
            int row = numberOfRow + 1;
            outputString.append(Integer.toString(row)).append(". ").append("  POST Status code : ").append(arrayOfPostResult[numberOfRow].getStatusCode().toString()).append(",").append(" Response time : ").append(String.valueOf(arrayOfPostResult[numberOfRow].getResponseTime())).append(" ms ");
            outputString.append(" || ").append(" GET Status code : ").append(arrayOfGetResult[numberOfRow].getStatusCode().toString()).append(",").append(" Response time : ").append(String.valueOf(arrayOfGetResult[numberOfRow].getResponseTime())).append(" ms ").append(",").append( "comparison result :  ").append(compare(arrayOfPostResult[numberOfRow],arrayOfGetResult[numberOfRow]));
            outputString.append("\n");
        }
        outputString.append("=========================================================================================================================================\n");
        outputString.append("POST method success rate : ").append(String.valueOf((counterPost * 100) / configuration.getRequestTime())).append("%");
        outputString.append("\n");
        outputString.append("GET method success rate : ").append(String.valueOf((counterGet * 100) / configuration.getRequestTime())).append("%");
        outputString.append("\n");
        outputString.append("GET method correct rate : ").append(String.valueOf((counterCorr *100) / configuration.getRequestTime())).append("%");

        outputString.close();
    }

    public void plot(PostResult[] arrayOfPostResult, GetResult[] arrayOfGetResult, Configuration configuration){

        xdim = 600;
        ydim = 600;
        xtic = 100;
        ytic = 10;
        xlabel = ("Number of Requests");
        sublabel1 = ("POST: black");
        sublabel2 = ("GET: red");
        ylabel = ("Respond Time");
        title = ylabel + " versus " + xlabel + " (Thread" + Thread.currentThread().getId() + ")" ;
        size = configuration.getRequestTime();

        long[] x = new long[size];
        long[] yP = new long[size];
        long[] yG = new long[size];

        for(int i = 0; i < size; i++)
            x[i] = i + 1;
        for(int i = 0; i < size; i++)
            yP[i] = arrayOfPostResult[i].getResponseTime();
        for(int i = 0; i < size; i++)
            yG[i] = arrayOfGetResult[i].getResponseTime();


        xmax = x[0];
        xmin = x[0];
//        ymax = yP[0];
//        ymin = yP[0];
        ymax = max(yP[0],yG[0]);
        ymin = min(yP[0],yG[0]);

        for (int i=0; i < size; i++){
            if (x[i] > xmax) {
                xmax = x[i];
            }
            if (x[i] < xmin) {
                xmin = x[i];
            }
            if (max(yP[i], yG[i]) > ymax) {
//                ymax = yP[i];
                ymax = max(yP[i], yG[i]);
            }
//            if (min(yP[i], yG[i]) < ymin) {
////                ymin = yP[i];
//                ymin = min(yP[i], yG[i]);
//            }
            ymin = 0;
        }

        xx = new int[size];
//        yy = new int[size];
        yyP = new int[size];
        yyG = new int[size];

        //xx and yy are the scaled x and y used for plotting
        for (int i=0; i < size; i++){
            xx[i] = (int)(50 + ((x[i]-xmin)/(xmax-xmin)) * (xdim-100));
            yyP[i] = (int)((ydim - 50) - (((yP[i]-ymin)/(ymax-ymin)) * (ydim-100)));
            yyG[i] = (int)((ydim - 50) - (((yG[i]-ymin)/(ymax-ymin)) * (ydim-100)));
        }

        //Find Zero point on y-axis required for drawing the axes
        if ((ymax*ymin) < 0){
            yzero = (int)((ydim - 50) - (((0-ymin)/(ymax-ymin)) * (ydim-100)));
        }
        else{
            yzero = (int)((ydim - 50) - ((0/(ymax-ymin)) * (ydim-100)));
        }

        //Find zero point on x-axis required for drawing the axes
        if ((xmax*xmin) < 0) {
            xzero = (int)(50 + (((0-xmin)/(xmax-xmin)) * (xdim-100)));
        }
        else{
            xzero = (int)(50 + ((0/(xmax-xmin)) * (xdim-100)));
        }

        //Now ready to plot the results
        repaint();
    }

    public void paint(Graphics g){

        Font f1 = new Font("TimesRoman", Font.PLAIN, 10);
        g.setFont(f1);

//First draw the axes

//y-axis

        g.drawLine(xzero, 50, xzero, ydim-50);
        g.drawLine(xzero, 50, (xzero - 5), 55);
        g.drawLine(xzero, 50, (xzero + 5), 55);

//x-axis

        g.drawLine(50, yzero, xdim-50, yzero);
        g.drawLine((xdim-50), yzero, (xdim-55), (yzero + 5));
        g.drawLine((xdim-50), yzero, (xdim-55), (yzero - 5));

//Initialise the labelling taking into account the xtic and ytic values

        //x-axis labels

        if (xmin <= 0){
            xpoint = xmin - (xmin%xtic);
        }else{
            xpoint = xmin - (xmin%xtic) + xtic;
        }

        do{
            xdraw = (int) (50 + (((xpoint-xmin)/(xmax-xmin))*(xdim-100)));
            g.drawString(xpoint + "", xdraw, (yzero+10));
            xpoint = xpoint + xtic;
        }while (xpoint <= xmax);

        if (ymin <= 0){
            ypoint = ymin - (ymin%ytic);
        }else{
            ypoint = ymin - (ymin%ytic) + ytic;
        }

        do{
            ydraw = (int) ((ydim - 50) - (((ypoint - ymin)/(ymax-ymin))*(ydim-100)));
            g.drawString(ypoint + "", (xzero - 20), ydraw);
            ypoint = ypoint + ytic;
        }while (ypoint <= ymax);
//Titles and labels
        Font f2 = new Font("TimesRoman", Font.BOLD, 14);
        g.setFont(f2);
        g.drawString(xlabel, (xdim - 100), (yzero + 20));
        g.drawString(ylabel, (xzero - 25), 40);
        g.drawString(sublabel1, (xdim - 100), (yzero + 40));
        g.drawString(sublabel2, (xdim - 100), (yzero + 60));
        g.drawString(title, (xdim/2 - 75), 20);

// Draw Lines

        for (int j = 0; j < size-1; j++)
        {

            g.drawLine(xx[j], yyP[j], xx[j+1], yyP[j+1]);

        }

        for (int j = 0; j < size-1; j++)
        {

            g.setColor(Color.RED);
            g.drawLine(xx[j], yyG[j], xx[j+1], yyG[j+1]);
        }
    }

    private String compare(PostResult postResult, GetResult getResult){
        if((Objects.equals(postResult.getJsonPostBodies().get(0).getUsername(), getResult.getJsonPostBodies().get(0).getUsername()))
                && (Objects.equals(postResult.getJsonPostBodies().get(0).getDestination(), getResult.getJsonPostBodies().get(0).getDestination()))
                && (Objects.equals(postResult.getJsonPostBodies().get(0).getFee(), getResult.getJsonPostBodies().get(0).getFee()))
                && (Objects.equals(postResult.getJsonPostBodies().get(0).getPickup(), getResult.getJsonPostBodies().get(0).getPickup()))
                && (Objects.equals(postResult.getJsonPostBodies().get(0).getProvider(), getResult.getJsonPostBodies().get(0).getProvider()))
                )
            return "passed";
        else {
            return "failed";
        }
    }

    private long min(long long1, long long2){
        if(long1 < long2)
            return long1;
        else
            return long2;
    }

    private long max(long long1, long long2){
        if(long1 > long2)
            return long1;
        else
            return long2;
    }
}
