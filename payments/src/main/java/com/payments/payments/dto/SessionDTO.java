package com.payments.payments.dto;

import java.util.Map;

public class SessionDTO {

    private String userId;
    private String sessionUrl;
    private String sessionId;
    private Map<String, String> data;

    public SessionDTO() {
    }


    public SessionDTO(String userId, String sessionUrl, String sessionId, Map<String, String> data) {
        this.userId = userId;
        this.sessionUrl = sessionUrl;
        this.sessionId = sessionId;
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionUrl() {
        return sessionUrl;
    }

    public void setSessionUrl(String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
