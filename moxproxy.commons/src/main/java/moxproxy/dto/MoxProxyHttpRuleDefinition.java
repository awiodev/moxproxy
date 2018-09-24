package moxproxy.dto;

import java.util.List;

public class MoxProxyHttpRuleDefinition {

    private String method;
    private String pathPattern;
    private String body;
    private List<MoxProxyHeader> headers;
    private int statusCode;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
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
