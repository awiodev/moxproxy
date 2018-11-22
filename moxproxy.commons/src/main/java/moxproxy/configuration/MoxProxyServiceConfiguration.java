package moxproxy.configuration;

import moxproxy.interfaces.IMoxProxyServiceConfiguration;

public class MoxProxyServiceConfiguration implements IMoxProxyServiceConfiguration {

    private int port = 89;
    private Iterable<String>  urlWhiteListForTrafficRecorder;
    private boolean matchSessionIdStrategy = false;

    public MoxProxyServiceConfiguration(){
    }

    public MoxProxyServiceConfiguration(int port, Iterable<String> urlWhiteListForTrafficRecorder, boolean matchSessionIdStrategy){
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
