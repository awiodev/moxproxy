package services;

import interfaces.IMoxProxyDatabase;
import interfaces.IProxyServer;
import interfaces.IProxyServiceConfiguration;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class MoxProxyServer extends MoxProxyService implements IProxyServer {

    private HttpProxyServer proxyServer;

    @Autowired
    private IProxyServiceConfiguration configuration;

    @Autowired
    private HttpFilters httpFilters;

    @Autowired
    private IMoxProxyDatabase database;


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
                .withFiltersSource(new HttpFiltersSourceAdapter() {
                    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                        return httpFilters;
                    }
                })
                .start();
    }

    @Override
    public void stopServer() {
        proxyServer.stop();
        database.stopDatabase();
    }
}
