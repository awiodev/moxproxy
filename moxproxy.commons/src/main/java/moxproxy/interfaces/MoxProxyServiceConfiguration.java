package moxproxy.interfaces;

public interface MoxProxyServiceConfiguration {

    int getProxyPort();
    Iterable<String>  getUrlWhiteListForTrafficRecorder();
    boolean isMatchSessionIdStrategy();
}
