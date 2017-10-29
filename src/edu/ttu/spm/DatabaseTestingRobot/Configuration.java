package edu.ttu.spm.DatabaseTestingRobot;

public class Configuration {

    private String baseURL;
    private int requestTime;

    private static final int READ_TIMEOUT = 30000; // seconds
    private static final int CONNECTION_TIMEOUT = 30000; // seconds

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

    public static int getReadTimeout() {
        return READ_TIMEOUT;
    }

    public static int getConnectionTimeout() {
        return CONNECTION_TIMEOUT;
    }
}
