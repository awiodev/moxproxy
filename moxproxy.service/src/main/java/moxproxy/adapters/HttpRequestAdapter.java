package moxproxy.adapters;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;

public class HttpRequestAdapter extends BaseHttpTrafficAdapter implements IHttpRequestAdapter {

    public HttpRequestAdapter(HttpObject request){
        super(request);
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
