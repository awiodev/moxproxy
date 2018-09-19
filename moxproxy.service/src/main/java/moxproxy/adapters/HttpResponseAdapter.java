package moxproxy.adapters;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;

public class HttpResponseAdapter extends BaseHttpTrafficAdapter implements IHttpResponseAdapter {

    public HttpResponseAdapter(HttpObject httpObject) {
        super(httpObject);
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
        return ((FullHttpRequest)getHttpObject()).method();
    }

    @Override
    protected String getUrl() {
        return ((FullHttpRequest)getHttpObject()).uri();
    }

    @Override
    public int statusCode() {
        return ((FullHttpResponse)getHttpObject()).status().code();
    }
}
