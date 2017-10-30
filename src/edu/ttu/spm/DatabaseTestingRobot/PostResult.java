package edu.ttu.spm.DatabaseTestingRobot;

public class PostResult {
    private Integer statusCode;
    private long responseTime;

    public PostResult() {
    }

    public PostResult(int statusCode, long responseTime) {
        this.statusCode = statusCode;
        this.responseTime = responseTime;
    }

    public Integer getStatusCode() {
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
