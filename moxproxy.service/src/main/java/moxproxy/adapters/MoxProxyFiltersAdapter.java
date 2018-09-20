package moxproxy.adapters;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyRulesMatcher;
import org.littleshoot.proxy.HttpFiltersAdapter;

import java.util.List;

public class MoxProxyFiltersAdapter extends HttpFiltersAdapter {

    private IMoxProxyRulesMatcher matcher;

    public MoxProxyFiltersAdapter(HttpRequest originalRequest, ChannelHandlerContext ctx, IMoxProxyRulesMatcher matcher) {
        super(originalRequest, ctx);
        this.matcher = matcher;
    }

    @Override
    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
        if(httpObject instanceof FullHttpRequest){
            IHttpRequestAdapter requestAdapter = new HttpRequestAdapter(httpObject, originalRequest);
            List<MoxProxyRule> result = matcher.match(requestAdapter);
            System.out.println();
        }
        return null;
    }

    @Override
    public HttpObject serverToProxyResponse(HttpObject httpObject) {
        if(httpObject instanceof FullHttpResponse){
            IHttpResponseAdapter responseAdapter = new HttpResponseAdapter(httpObject, originalRequest);
            List<MoxProxyRule> result = matcher.match(responseAdapter);
            System.out.println();
        }
        return httpObject;
    }
}
