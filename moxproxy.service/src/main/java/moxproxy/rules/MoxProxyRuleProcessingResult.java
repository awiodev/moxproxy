package moxproxy.rules;

import io.netty.handler.codec.http.FullHttpResponse;

public class MoxProxyRuleProcessingResult {
    private boolean respond;
    private FullHttpResponse response;

    public boolean isRespond() {
        return respond;
    }

    public void setRespond(boolean respond) {
        this.respond = respond;
    }

    public FullHttpResponse getResponse() {
        return response;
    }

    public void setResponse(FullHttpResponse response) {
        this.response = response;
    }
}
