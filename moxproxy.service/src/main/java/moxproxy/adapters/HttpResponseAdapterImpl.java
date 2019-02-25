package moxproxy.adapters;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import moxproxy.interfaces.HttpResponseAdapter;

public class HttpResponseAdapterImpl extends BaseHttpTrafficAdapter implements HttpResponseAdapter {

    HttpResponseAdapterImpl(HttpObject httpObject, HttpRequest originalRequest, String connectedUrl) {
        super(httpObject, originalRequest, connectedUrl);
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
    public int statusCode() {
        return ((FullHttpResponse)getHttpObject()).status().code();
    }
}
