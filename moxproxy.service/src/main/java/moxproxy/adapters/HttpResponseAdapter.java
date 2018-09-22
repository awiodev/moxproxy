package moxproxy.adapters;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import moxproxy.interfaces.IHttpResponseAdapter;

public class HttpResponseAdapter extends BaseHttpTrafficAdapter implements IHttpResponseAdapter {

    public HttpResponseAdapter(HttpObject httpObject, HttpRequest originalRequest) {
        super(httpObject, originalRequest);
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
        return originalRequest.uri();
    }

    @Override
    public int statusCode() {
        return ((FullHttpResponse)getHttpObject()).status().code();
    }
}
