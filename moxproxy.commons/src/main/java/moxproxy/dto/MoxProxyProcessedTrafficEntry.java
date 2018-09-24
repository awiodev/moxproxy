package moxproxy.dto;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<MoxProxyHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<MoxProxyHeader> headers) {
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
