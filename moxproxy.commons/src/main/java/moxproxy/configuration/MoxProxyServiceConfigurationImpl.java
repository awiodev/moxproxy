package moxproxy.configuration;

import moxproxy.interfaces.MoxProxyServiceConfiguration;

public class MoxProxyServiceConfigurationImpl implements MoxProxyServiceConfiguration {

    private int port = 89;
    private Iterable<String>  urlWhiteListForTrafficRecorder;
    private boolean matchSessionIdStrategy = false;

    public MoxProxyServiceConfigurationImpl(){
    }

    public MoxProxyServiceConfigurationImpl(int port, Iterable<String> urlWhiteListForTrafficRecorder, boolean matchSessionIdStrategy){
        this.port = port;
        this.urlWhiteListForTrafficRecorder = urlWhiteListForTrafficRecorder;
        this.matchSessionIdStrategy = matchSessionIdStrategy;
    }

    @Override
    public int getProxyPort() {
        return port;
    }

    @Override
    public Iterable<String> getUrlWhiteListForTrafficRecorder() {
        return urlWhiteListForTrafficRecorder;
    }

    @Override
    public boolean isMatchSessionIdStrategy() {
        return matchSessionIdStrategy;
    }
}
