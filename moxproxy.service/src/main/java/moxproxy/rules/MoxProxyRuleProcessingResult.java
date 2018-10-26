package moxproxy.rules;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;

public class MoxProxyRuleProcessingResult {

    private boolean respond;
    private boolean modifiedRequest;
    private HttpResponse response;
    private HttpObject request;

    public boolean isRespond() {
        return respond;
    }

    public void setRespond(boolean respond) {
        this.respond = respond;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public boolean isModifiedRequest() {
        return modifiedRequest;
    }

    public void setModifiedRequest(boolean modifiedRequest) {
        this.modifiedRequest = modifiedRequest;
    }

    public HttpObject getRequest() {
        return request;
    }

    public void setRequest(HttpObject request) {
        this.request = request;
    }
}
