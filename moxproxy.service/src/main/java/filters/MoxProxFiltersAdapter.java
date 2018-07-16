package filters;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.littleshoot.proxy.HttpFiltersAdapter;

public class MoxProxFiltersAdapter extends HttpFiltersAdapter {

    public MoxProxFiltersAdapter(HttpRequest originalRequest, ChannelHandlerContext ctx) {
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
