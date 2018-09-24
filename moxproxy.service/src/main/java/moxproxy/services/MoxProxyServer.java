package moxproxy.services;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import moxproxy.adapters.MoxProxyFiltersAdapter;
import moxproxy.interfaces.IEntityConverter;
import moxproxy.interfaces.*;
import moxproxy.interfaces.IMoxProxyRuleProcessor;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSource;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class MoxProxyServer extends MoxProxyService implements IMoxProxyServer {

    private HttpProxyServer proxyServer;

    @Autowired
    private IMoxProxyServiceConfiguration configuration;

    @Autowired
    private IMoxProxyTrafficRecorder recorder;

    @Autowired
    private IEntityConverter converter;

    @Autowired
    private IMoxProxyRuleProcessor processor;

    @Override
    public void startServer() {
        startDatabase();
        startProxyServer();
    }

    private void startDatabase(){
        database.startDatabase();
    }

    private void startProxyServer(){
        proxyServer = DefaultHttpProxyServer.bootstrap()
                .withPort(configuration.getProxyPort())
                .withAllowLocalOnly(false)
                .withFiltersSource(getFiltersSource())
                .start();
    }

    @Override
    public void stopServer() {
        proxyServer.stop();
        database.stopDatabase();
    }

    private HttpFiltersSource getFiltersSource(){
        return new HttpFiltersSource() {

            @Override
            public HttpFilters filterRequest(HttpRequest httpRequest, ChannelHandlerContext channelHandlerContext) {
                return new MoxProxyFiltersAdapter(httpRequest, channelHandlerContext, matcher, recorder, converter, processor);
            }

            @Override
            public int getMaximumRequestBufferSizeInBytes() {
                return getBufferSize();
            }

            @Override
            public int getMaximumResponseBufferSizeInBytes() {
                return getBufferSize();
            }

            private int getBufferSize(){
                return 10 * 1024 * 1024;
            }
        };
    }
}
