package moxproxy.configuration;

public interface MoxProxyClientConfiguration {

    String getBaseUrl();
    boolean useBasicAuth();
    String getUserName();
    String getPassword();
}
