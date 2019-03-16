package moxproxy.rules;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;

public class MoxProxyRuleProcessingResult {

    private HttpResponse response;
    private HttpObject request;
    private MoxProxyProcessingResultType moxProxyProcessingResultType;

    MoxProxyRuleProcessingResult(){
        moxProxyProcessingResultType = MoxProxyProcessingResultType.DO_NOTHING;
    }

    public MoxProxyProcessingResultType getMoxProxyProcessingResultType() {
        return moxProxyProcessingResultType;
    }

    void setMoxProxyProcessingResultType(MoxProxyProcessingResultType moxProxyProcessingResultType) {
        this.moxProxyProcessingResultType = moxProxyProcessingResultType;
    }

    public HttpResponse getResponse() {
        return response;
    }

    void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public HttpObject getRequest() {
        return request;
    }

    void setRequest(HttpObject request) {
        this.request = request;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }
}
