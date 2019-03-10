package moxproxy.webservice.config;

public class WebServiceConfiguration {

    private int cleanupDelayInSeconds;
    private String basicAuthUserName;
    private String basicAuthPassword;

    public int getCleanupDelayInSeconds() {
        return cleanupDelayInSeconds;
    }

    String getBasicAuthUserName() {
        return basicAuthUserName;
    }

    String getBasicAuthPassword() {
        return basicAuthPassword;
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
}
