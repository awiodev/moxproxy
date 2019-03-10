package moxproxy.interfaces;

import org.littleshoot.proxy.mitm.Authority;

import java.util.List;

public interface MoxProxyServiceConfiguration {

    int getProxyPort();
    List<String> getUrlWhiteListForTrafficRecorder();
    boolean isMatchSessionIdStrategy();
    Authority getAuthority();
}
