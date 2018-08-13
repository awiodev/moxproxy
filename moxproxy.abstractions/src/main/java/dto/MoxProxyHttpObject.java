package dto;

public class MoxProxyHttpObject {

    private String method;
    private String path;
    private String body;
    private Iterable<MoxProxyHeader> headers;
    private int statusCode;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Iterable<MoxProxyHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(Iterable<MoxProxyHeader> headers) {
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
