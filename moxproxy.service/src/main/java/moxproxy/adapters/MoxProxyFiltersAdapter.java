package moxproxy.adapters;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import moxproxy.interfaces.IMoxProxyRulesMatcher;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.springframework.beans.factory.annotation.Autowired;

public class MoxProxyFiltersAdapter extends HttpFiltersAdapter {

    @Autowired
    private IMoxProxyRulesMatcher rules;

    public MoxProxyFiltersAdapter(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        super(originalRequest, ctx);
    }

    @Override
    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
        if(httpObject instanceof FullHttpRequest){
            IHttpRequestAdapter requestAdapter = new HttpRequestAdapter(httpObject, originalRequest);
            System.out.println();
        }
        return null;
    }

    @Override
    public HttpObject serverToProxyResponse(HttpObject httpObject) {
        if(httpObject instanceof FullHttpResponse){
            IHttpResponseAdapter responseAdapter = new HttpResponseAdapter(httpObject, originalRequest);
            System.out.println();
        }
        return httpObject;
    }
}
