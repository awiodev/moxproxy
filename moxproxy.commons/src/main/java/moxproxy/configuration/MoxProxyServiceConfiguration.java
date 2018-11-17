package moxproxy.configuration;

import moxproxy.interfaces.IMoxProxyServiceConfiguration;

public class MoxProxyServiceConfiguration implements IMoxProxyServiceConfiguration {

    private int port = 89;

    public MoxProxyServiceConfiguration(){
    }

    public MoxProxyServiceConfiguration(int port){
        this.port = port;
    }

    @Override
    public int getProxyPort() {
        return port;
    }
}
