package moxproxy.configuration;

import moxproxy.interfaces.MoxProxyServiceConfiguration;

import java.util.List;

public class MoxProxyServiceConfigurationImpl implements MoxProxyServiceConfiguration {

    private int port = 89;
    private List<String> urlWhiteListForTrafficRecorder;
    private boolean matchSessionIdStrategy = false;

    public MoxProxyServiceConfigurationImpl(){
    }

    public MoxProxyServiceConfigurationImpl(int port, List<String> urlWhiteListForTrafficRecorder, boolean matchSessionIdStrategy){
        this.port = port;
        this.urlWhiteListForTrafficRecorder = urlWhiteListForTrafficRecorder;
        this.matchSessionIdStrategy = matchSessionIdStrategy;
    }

    @Override
    public int getProxyPort() {
        return port;
    }

    @Override
    public List<String> getUrlWhiteListForTrafficRecorder() {
        return urlWhiteListForTrafficRecorder;
    }

    @Override
    public boolean isMatchSessionIdStrategy() {
        return matchSessionIdStrategy;
    }
}
