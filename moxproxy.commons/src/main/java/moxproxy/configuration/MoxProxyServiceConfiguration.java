package moxproxy.configuration;

import moxproxy.interfaces.IMoxProxyServiceConfiguration;

public class MoxProxyServiceConfiguration implements IMoxProxyServiceConfiguration {

    @Override
    public int getProxyHost() {
        return 0;
    }

    @Override
    public int getProxyPort() {
        return 0;
    }
}
