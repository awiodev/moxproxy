package moxproxy.configuration;

import moxproxy.interfaces.IMoxProxyServiceConfiguration;

public class MoxProxyServiceConfiguration implements IMoxProxyServiceConfiguration {

    @Override
    public String getProxyHost() {
        return "localhost";
    }

    @Override
    public int getProxyPort() {
        return 8081;
    }
}
