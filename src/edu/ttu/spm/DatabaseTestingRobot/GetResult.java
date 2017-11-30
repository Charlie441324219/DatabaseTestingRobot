package edu.ttu.spm.DatabaseTestingRobot;

import java.util.List;

public class GetResult {
    private Integer statusCode;
    private long responseTime;
    private List<JsonPostBody> jsonPostBodies;

    public GetResult(Integer statusCode, long responseTime, List<JsonPostBody> jsonPostBodies) {
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.jsonPostBodies = jsonPostBodies;
    }
    public GetResult() {
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public List<JsonPostBody> getJsonPostBodies() {
        return jsonPostBodies;
    }

    public void setJsonPostBodies(List<JsonPostBody> jsonPostBodies) {
        this.jsonPostBodies = jsonPostBodies;
    }
}
