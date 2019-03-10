package moxproxy.configuration;

import moxproxy.interfaces.MoxProxyServiceConfiguration;
import org.littleshoot.proxy.mitm.Authority;

import java.util.List;

public class MoxProxyServiceConfigurationImpl implements MoxProxyServiceConfiguration {

    private int port = 89;
    private List<String> urlWhiteListForTrafficRecorder;
    private boolean matchSessionIdStrategy;
    private Authority authority;


    public MoxProxyServiceConfigurationImpl(){
    }

    public MoxProxyServiceConfigurationImpl(int port, List<String> urlWhiteListForTrafficRecorder, boolean matchSessionIdStrategy,
                                            Authority authority){
        this.port = port;
        this.urlWhiteListForTrafficRecorder = urlWhiteListForTrafficRecorder;
        this.matchSessionIdStrategy = matchSessionIdStrategy;
        this.authority = authority;
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

    @Override
    public Authority getAuthority() {
        return authority;
    }

}
