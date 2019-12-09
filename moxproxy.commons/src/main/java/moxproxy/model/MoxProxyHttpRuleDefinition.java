package moxproxy.model;

import java.util.List;

public class MoxProxyHttpRuleDefinition {

    private String method;
    private String pathPattern;
    private String body;
    private List<MoxProxyHeader> headers;
    private int statusCode;

    public MoxProxyHttpRuleDefinition() { }

    public MoxProxyHttpRuleDefinition(String method, String pathPattern, String body, List<MoxProxyHeader> headers, int statusCode) {
        this.method = method;
        this.pathPattern = pathPattern;
        this.body = body;
        this.headers = headers;
        this.statusCode = statusCode;
    }

    public String getMethod() {
        return method;
    }

    public String getPathPattern() {
        return pathPattern;
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
