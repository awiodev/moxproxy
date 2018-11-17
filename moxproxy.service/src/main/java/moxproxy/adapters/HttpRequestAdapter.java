package moxproxy.adapters;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import moxproxy.interfaces.IHttpRequestAdapter;

public class HttpRequestAdapter extends BaseHttpTrafficAdapter implements IHttpRequestAdapter {

    public HttpRequestAdapter(HttpObject request, HttpRequest originalRequest, String connectedUrl){
        super(request, originalRequest, connectedUrl);
    }

    @Override
    protected HttpHeaders getHeaders() {
        return ((FullHttpRequest)getHttpObject()).headers();
    }

    @Override
    protected ByteBuf getContent() {
        return ((FullHttpRequest)getHttpObject()).content();
    }

    @Override
    protected HttpMethod getMethod() {
        return ((FullHttpRequest)getHttpObject()).method();
    }

    @Override
    protected String getUrl() {
        return connectedUrl + ((FullHttpRequest)getHttpObject()).uri();
    }
}
