package moxproxy.model;

import java.util.List;

public class MoxProxyProcessedTrafficEntry extends MoxProxyTransportEntity {

    private String sessionId;
    private String method;
    private String url;
    private String body;
    private List<MoxProxyHeader> headers;
    private int statusCode;

    public MoxProxyProcessedTrafficEntry(){}

    public MoxProxyProcessedTrafficEntry(String sessionId, String method, String url, String body, List<MoxProxyHeader> headers) {
        this.sessionId = sessionId;
        this.method = method;
        this.url = url;
        this.body = body;
        this.headers = headers;
    }

    public MoxProxyProcessedTrafficEntry(String sessionId, String method, String url, String body, List<MoxProxyHeader> headers, int statusCode) {
        this.sessionId = sessionId;
        this.method = method;
        this.url = url;
        this.body = body;
        this.headers = headers;
        this.statusCode = statusCode;
    }

    public String getSessionId() {
        return sessionId;
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
