package moxproxy.dto;

import java.util.Date;
import java.util.UUID;

public class MoxProxyProcessedTrafficEntry {

    private String id;
    private String sessionId;
    private MoxProxyHttpObject httpObject;
    private Date timestamp;

    public MoxProxyProcessedTrafficEntry(){
        id = UUID.randomUUID().toString();
        timestamp = new Date();
    }

    public String getId() {
        return id;
    }

    public MoxProxyHttpObject getHttpObject() {
        return httpObject;
    }

    public void setHttpObject(MoxProxyHttpObject httpObject) {
        this.httpObject = httpObject;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
