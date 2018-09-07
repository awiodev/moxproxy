package moxproxy.filters;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
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
        // TODO: implement your filtering here
        return null;
    }

    @Override
    public HttpObject serverToProxyResponse(HttpObject httpObject) {
        // TODO: implement your filtering here
        return httpObject;
    }
}
