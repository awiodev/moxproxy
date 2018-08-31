package moxproxy.dto;

import java.util.Date;
import java.util.UUID;

public class MoxProxyProcessedTrafficEntry {

    private String id;
    private String sessionId;
    private MoxProxyHttpObject request;
    private MoxProxyHttpObject response;
    private Date timestamp;

    public MoxProxyProcessedTrafficEntry(){
        id = UUID.randomUUID().toString();
        timestamp = new Date();
    }

    public String getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public MoxProxyHttpObject getRequest() {
        return request;
    }

    public void setRequest(MoxProxyHttpObject request) {
        this.request = request;
    }

    public MoxProxyHttpObject getResponse() {
        return response;
    }

    public void setResponse(MoxProxyHttpObject response) {
        this.response = response;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
