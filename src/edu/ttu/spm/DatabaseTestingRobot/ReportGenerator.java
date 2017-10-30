package edu.ttu.spm.DatabaseTestingRobot;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ReportGenerator {
    public void outputReport(PostResult[] arrayOfPostResult, Configuration configuration) throws FileNotFoundException {

        String fileName = "report.txt";
        PrintWriter outputString = new PrintWriter(fileName);

        outputString.write("report ");
        outputString.append("\n");

        for (Integer numberOfRow = 0; numberOfRow < configuration.getRequestTime(); numberOfRow ++) {
            outputString.append(numberOfRow.toString()).append("  status code : ").append(arrayOfPostResult[numberOfRow].getStatusCode().toString()).append(",").append(" respond time : ").append(String.valueOf(arrayOfPostResult[numberOfRow].getResponseTime())).append(" ms ");
            outputString.append("\n");
        }
        outputString.close();


    }
}
