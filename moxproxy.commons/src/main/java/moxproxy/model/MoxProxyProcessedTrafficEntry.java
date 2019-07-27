package moxproxy.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MoxProxyProcessedTrafficEntry {

    private String id;
    private String sessionId;
    private String method;
    private String url;
    private String body;
    private List<MoxProxyHeader> headers;
    private int statusCode;
    private Date timestamp;

    public MoxProxyProcessedTrafficEntry(String sessionId, String method, String url, String body, List<MoxProxyHeader> headers) {
        this();
        this.sessionId = sessionId;
        this.method = method;
        this.url = url;
        this.body = body;
        this.headers = headers;
    }

    public MoxProxyProcessedTrafficEntry(String sessionId, String method, String url, String body, List<MoxProxyHeader> headers, int statusCode) {
        this();
        this.sessionId = sessionId;
        this.method = method;
        this.url = url;
        this.body = body;
        this.headers = headers;
        this.statusCode = statusCode;
    }

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

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public List<MoxProxyHeader> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
