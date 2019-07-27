package moxproxy.adapters;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import moxproxy.interfaces.HttpTrafficAdapter;
import moxproxy.model.MoxProxyHeader;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpResponseAdapterImpl extends BaseHttpTrafficAdapter implements HttpTrafficAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpResponseAdapterImpl.class);

    HttpResponseAdapterImpl(HttpObject httpObject, HttpRequest originalRequest, String connectedUrl, boolean isSessionIdStrategy, boolean isContentRead) {
        super(httpObject, originalRequest, connectedUrl, isSessionIdStrategy, isContentRead);
    }

    @Override
    protected HttpHeaders getHeaders() {
        return ((FullHttpResponse)getHttpObject()).headers();
    }

    @Override
    protected ByteBuf getContent() {
        try{
            return ((FullHttpResponse)getHttpObject()).content();
        }catch (Exception e){
            LOG.error("Not able to extract http response content", e);
            return null;
        }
    }

    @Override
    protected HttpMethod getMethod() {
        return originalRequest.method();
    }

    @Override
    protected String getUrl() {
        return connectedUrl + originalRequest.uri();
    }

    @Override
    protected void getSessionId() {
        if (isSessionIdStrategy) {
            List<MoxProxyHeader> requestHeaders = transformToProxyHeaders(originalRequest.headers());
            extractSessionId(requestHeaders);
        }
    }

    private int statusCode() {
        return ((FullHttpResponse)getHttpObject()).status().code();
    }

    @Override
    public MoxProxyProcessedTrafficEntry trafficEntry() {
        return new MoxProxyProcessedTrafficEntry(sessionId(), method(), url(), body(), headers(), statusCode());
    }
}
