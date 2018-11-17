package moxproxy.configuration;

import moxproxy.interfaces.IMoxProxyServiceConfiguration;

public class MoxProxyServiceConfiguration implements IMoxProxyServiceConfiguration {

    private int port = 89;
    private Iterable<String>  urlWhiteListForTrafficRecorder;

    public MoxProxyServiceConfiguration(){
    }

    public MoxProxyServiceConfiguration(int port, Iterable<String> urlWhiteListForTrafficRecorder){
        this.port = port;
        this.urlWhiteListForTrafficRecorder = urlWhiteListForTrafficRecorder;
    }

    @Override
    public int getProxyPort() {
        return port;
    }

    @Override
    public Iterable<String> getUrlWhiteListForTrafficRecorder() {
        return urlWhiteListForTrafficRecorder;
    }
}
