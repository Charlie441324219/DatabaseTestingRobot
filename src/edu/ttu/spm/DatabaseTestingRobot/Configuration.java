package edu.ttu.spm.DatabaseTestingRobot;

public class Configuration {

    private String baseURL;
    private int requestTime;

    public Configuration() {
    }

    public Configuration(String baseURL, int requestTime) {
        this.baseURL = baseURL;
        this.requestTime = requestTime;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public int getRequestTime() {
        return requestTime;
    }
}
