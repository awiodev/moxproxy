package server;

import interfaces.IProxyServer;
import interfaces.IProxyServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoxProxyServer implements IProxyServer {

    private IProxyServerConfiguration configuration;

    @Autowired
    public MoxProxyServer(IProxyServerConfiguration configuration){
        this.configuration = configuration;
    }

    @Override
    public void startServer() {

    }

    @Override
    public void stopServer() {

    }
}
