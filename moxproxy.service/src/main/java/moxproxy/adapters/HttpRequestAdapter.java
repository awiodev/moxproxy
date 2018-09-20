package moxproxy.adapters;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;

public class HttpRequestAdapter extends BaseHttpTrafficAdapter implements IHttpRequestAdapter {

    public HttpRequestAdapter(HttpObject request, HttpRequest originalRequest){
        super(request, originalRequest);
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
        return ((FullHttpRequest)getHttpObject()).uri();
    }
}
