package moxproxy.rules;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;

public class MoxProxyRuleProcessingResult {

    private HttpResponse response;
    private HttpObject request;
    private MoxProxyProcessingResultType moxProxyProcessingResultType;

    public MoxProxyRuleProcessingResult(){
        moxProxyProcessingResultType = MoxProxyProcessingResultType.DO_NOTING;
    }

    public MoxProxyProcessingResultType getMoxProxyProcessingResultType() {
        return moxProxyProcessingResultType;
    }

    public void setMoxProxyProcessingResultType(MoxProxyProcessingResultType moxProxyProcessingResultType) {
        this.moxProxyProcessingResultType = moxProxyProcessingResultType;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public HttpObject getRequest() {
        return request;
    }

    public void setRequest(HttpObject request) {
        this.request = request;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }
}
