package services;

import interfaces.IMoxProxyLocalService;
import interfaces.IProxyServer;
import org.springframework.beans.factory.annotation.Autowired;

public class LocalMoxProxyService extends MoxProxyService implements IMoxProxyLocalService {

    private IProxyServer proxyServer;

    @Autowired
    public LocalMoxProxyService(IProxyServer proxyServer){
        this.proxyServer = proxyServer;
    }

    @Override
    public void startService() {
        proxyServer.startServer();
    }

    @Override
    public void stopService() {
        proxyServer.stopServer();
    }
}
