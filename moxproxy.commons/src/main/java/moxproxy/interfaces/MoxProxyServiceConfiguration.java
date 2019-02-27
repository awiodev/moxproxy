package moxproxy.interfaces;

import java.util.List;

public interface MoxProxyServiceConfiguration {

    int getProxyPort();
    List<String> getUrlWhiteListForTrafficRecorder();
    boolean isMatchSessionIdStrategy();
}
