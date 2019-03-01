package moxproxy.webservice.config;

import java.util.List;

public class WebServiceConfiguration {

    private int proxyPort;
    private int webServicePort;
    private int cleanupDelayInSeconds;
    private String basicAuthUserName;
    private String basicAuthPassword;
    private List<String> urlWhiteListForTrafficRecorder;
    private boolean sessionIdMatchStrategy;

    int getProxyPort() {
        return proxyPort;
    }

    int getWebServicePort() {
        return webServicePort;
    }

    public int getCleanupDelayInSeconds() {
        return cleanupDelayInSeconds;
    }

    String getBasicAuthUserName() {
        return basicAuthUserName;
    }

    String getBasicAuthPassword() {
        return basicAuthPassword;
    }

    List<String> getUrlWhiteListForTrafficRecorder() {
        return urlWhiteListForTrafficRecorder;
    }

    boolean isSessionIdMatchStrategy() {
        return sessionIdMatchStrategy;
    }

    void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    void setWebServicePort(int webServicePort) {
        this.webServicePort = webServicePort;
    }

    void setCleanupDelayInSeconds(int cleanupDelayInSeconds) {
        this.cleanupDelayInSeconds = cleanupDelayInSeconds;
    }

    void setBasicAuthUserName(String basicAuthUserName) {
        this.basicAuthUserName = basicAuthUserName;
    }

    void setBasicAuthPassword(String basicAuthPassword) {
        this.basicAuthPassword = basicAuthPassword;
    }

    void setUrlWhiteListForTrafficRecorder(List<String> urlWhiteListForTrafficRecorder) {
        this.urlWhiteListForTrafficRecorder = urlWhiteListForTrafficRecorder;
    }

    void setSessionIdMatchStrategy(boolean sessionIdMatchStrategy) {
        this.sessionIdMatchStrategy = sessionIdMatchStrategy;
    }
}
