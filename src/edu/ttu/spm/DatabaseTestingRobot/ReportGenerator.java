package edu.ttu.spm.DatabaseTestingRobot;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ReportGenerator extends JPanel {
    String xlabel, ylabel, title;
    int xdim, ydim, yzero, xzero, xdraw, ydraw;
    double xtic, ytic, xpoint, ypoint;
    double  xmax, xmin, ymax, ymin;
    int size;
    int[] xx;
    int[] yy;
    int counter = 0;

    public void outputReport(PostResult[] arrayOfPostResult, Configuration configuration) throws FileNotFoundException {

        String fileName = "report.txt";
        PrintWriter outputString = new PrintWriter(fileName);

        outputString.write("report ");
        outputString.append("\n");

        for (Integer numberOfRow = 0; numberOfRow < configuration.getRequestTime(); numberOfRow ++) {
            if(arrayOfPostResult[numberOfRow].getStatusCode() == 200)
                counter ++;
            outputString.append(numberOfRow.toString()  + ". ").append("  Status code : ").append(arrayOfPostResult[numberOfRow].getStatusCode().toString()).append(",").append(" Respond time : ").append(String.valueOf(arrayOfPostResult[numberOfRow].getResponseTime())).append(" ms ");
            outputString.append("\n");
        }
        outputString.append("success rate : " + counter/configuration.getRequestTime()*100 + "%");

        outputString.close();
    }

    public void plot(PostResult[] arrayOfPostResult, Configuration configuration){

        xdim = 600;
        ydim = 600;
        xtic = 100;
        ytic = 10;
        xlabel = ("Number of Requests");
        ylabel = ("Respond Time");
        title = ylabel + " versus " + xlabel;
        size = configuration.getRequestTime();

        long[] x = new long[size];
        long[] y = new long[size];

        for(int i = 0; i < size; i++)
            x[i] = i + 1;
        for (int i = 0; i < size; i++)
            y[i] = arrayOfPostResult[i].getResponseTime();

        xmax = x[0];
        xmin = x[0];
        ymax = y[0];
        ymin = y[0];

        for (int i=0; i < size; i++){
            if (x[i] > xmax) {
                xmax = x[i];
            }
            if (x[i] < xmin) {
                xmin = x[i];
            }
            if (y[i] > ymax) {
                ymax = y[i];
            }
            if (y[i] < ymin) {
                ymin = y[i];
            }
        }

        xx = new int[size];
        yy = new int[size];

        //xx and yy are the scaled x and y used for plotting
        for (int i=0; i < size; i++){
            xx[i] = (int)(50 + ((x[i]-xmin)/(xmax-xmin)) * (xdim-100));
            yy[i] = (int)((ydim - 50) - (((y[i]-ymin)/(ymax-ymin)) * (ydim-100)));
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
        g.drawString(xlabel, (xdim - 100), (yzero + 25));
        g.drawString(ylabel, (xzero - 25), 40);
        g.drawString(title, (xdim/2 - 75), 20);

// Draw Lines

        for (int j = 0; j < size-1; j++)
        {

            g.drawLine(xx[j], yy[j], xx[j+1], yy[j+1]);
        }


    }
}
