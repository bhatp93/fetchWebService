package com.fetch.webservices.fetch_web_services.requestResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;

public class GetResponseObject {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer points;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
