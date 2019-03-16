package moxproxy.webservice;


import moxproxy.interfaces.MoxProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PreDestroy;

public class ApplicationShutdown {

    @Autowired
    @Qualifier("moxProxyServer")
    MoxProxy proxyServer;

    @PreDestroy
    public void onDestroy(){
        proxyServer.stopServer();
    }
}
