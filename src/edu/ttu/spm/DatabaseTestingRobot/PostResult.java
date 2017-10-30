package edu.ttu.spm.DatabaseTestingRobot;

public class PostResult {
    private int statusCode;
    private long responseTime;

    public PostResult() {
    }

    public PostResult(int statusCode, long responseTime) {
        this.statusCode = statusCode;
        this.responseTime = responseTime;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }
}
