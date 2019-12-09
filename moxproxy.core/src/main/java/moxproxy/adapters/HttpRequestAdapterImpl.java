package moxproxy.adapters;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import moxproxy.interfaces.HttpTrafficAdapter;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestAdapterImpl extends BaseHttpTrafficAdapter implements HttpTrafficAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpRequestAdapterImpl.class);

    HttpRequestAdapterImpl(HttpObject request, HttpRequest originalRequest, String connectedUrl, boolean isSessionIdStrategy, boolean isContentRead) {
        super(request, originalRequest, connectedUrl, isSessionIdStrategy, isContentRead);
    }

    @Override
    protected HttpHeaders getHeaders() {
        return ((FullHttpRequest)getHttpObject()).headers();
    }

    @Override
    protected ByteBuf getContent() {
        try{
            return ((FullHttpRequest)getHttpObject()).content();
        }catch (Exception e){
            LOG.error("Not able to extract http request content", e);
            return null;
        }
    }

    @Override
    protected HttpMethod getMethod() {
        return ((FullHttpRequest)getHttpObject()).method();
    }

    @Override
    protected String getUrl() {
        return connectedUrl + ((FullHttpRequest)getHttpObject()).uri();
    }

    @Override
    protected void getSessionId() {
        if (isSessionIdStrategy) {
            extractSessionId();
        }
    }

    @Override
    public MoxProxyProcessedTrafficEntry trafficEntry() {
        return new MoxProxyProcessedTrafficEntry(sessionId(), method(), url(), body(), headers());
    }
}
