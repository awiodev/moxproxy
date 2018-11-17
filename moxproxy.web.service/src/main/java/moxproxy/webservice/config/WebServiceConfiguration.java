package moxproxy.webservice.config;

public class WebServiceConfiguration {

    private int proxyPort;
    private int webServicePort;
    private int cleanupDelayInSeconds;
    private String basicAuthUserName;
    private String basicAuthPassword;

    public int getProxyPort() {
        return proxyPort;
    }

    public int getWebServicePort() {
        return webServicePort;
    }

    public int getCleanupDelayInSeconds() {
        return cleanupDelayInSeconds;
    }

    public String getBasicAuthUserName() {
        return basicAuthUserName;
    }

    public String getBasicAuthPassword() {
        return basicAuthPassword;
    }
}
