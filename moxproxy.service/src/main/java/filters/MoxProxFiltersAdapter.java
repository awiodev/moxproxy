package filters;

import interfaces.IMoxProxyDatabase;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.littleshoot.proxy.HttpFiltersAdapter;

public class MoxProxFiltersAdapter extends HttpFiltersAdapter {

    private IMoxProxyDatabase moxProxyDatabase;

    public MoxProxFiltersAdapter(HttpRequest originalRequest, ChannelHandlerContext ctx, IMoxProxyDatabase moxProxyDatabase) {
        super(originalRequest, ctx);
        this.moxProxyDatabase = moxProxyDatabase;
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
