package moxproxy.interfaces;

public interface IMoxProxyServiceConfiguration {

    int getProxyPort();
    Iterable<String>  getUrlWhiteListForTrafficRecorder();
}
