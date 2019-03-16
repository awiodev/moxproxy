package moxproxy.adapters;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import moxproxy.interfaces.HttpResponseAdapter;
import moxproxy.model.MoxProxyHeader;

import java.util.List;

public class HttpResponseAdapterImpl extends BaseHttpTrafficAdapter implements HttpResponseAdapter {

    HttpResponseAdapterImpl(HttpObject httpObject, HttpRequest originalRequest, String connectedUrl, boolean isSessionIdStrategy) {
        super(httpObject, originalRequest, connectedUrl, isSessionIdStrategy);
    }

    @Override
    protected HttpHeaders getHeaders() {
        return ((FullHttpResponse)getHttpObject()).headers();
    }

    @Override
    protected ByteBuf getContent() {
        return ((FullHttpResponse)getHttpObject()).content();
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

    @Override
    public int statusCode() {
        return ((FullHttpResponse)getHttpObject()).status().code();
    }
}
