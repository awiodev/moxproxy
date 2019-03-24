package moxproxy.webservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SuppressWarnings("unused")
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class WebServiceConfiguration {

    private Service service;
    private Proxy proxy;
    private Mitm mitm;

    public Service getService(){
        return service;
    }

    Proxy getProxy(){
        return proxy;
    }

    Mitm getMitm() {
        return mitm;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setMitm(Mitm mitm) {
        this.mitm = mitm;
    }

    public static class Service{
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

        public void setCleanupDelayInSeconds(int cleanupDelayInSeconds) {
            this.cleanupDelayInSeconds = cleanupDelayInSeconds;
        }

        public void setBasicAuthUserName(String basicAuthUserName) {
            this.basicAuthUserName = basicAuthUserName;
        }

        public void setBasicAuthPassword(String basicAuthPassword) {
            this.basicAuthPassword = basicAuthPassword;
        }
    }

    public static class Proxy {
        private int proxyPort;
        private List<String> urlWhiteListForTrafficRecorder;
        private boolean sessionIdMatchStrategy;
        private boolean recordContent;

        int getProxyPort() {
            return proxyPort;
        }

        public void setProxyPort(int proxyPort) {
            this.proxyPort = proxyPort;
        }

        List<String> getUrlWhiteListForTrafficRecorder() {
            return urlWhiteListForTrafficRecorder;
        }

        public void setUrlWhiteListForTrafficRecorder(List<String> urlWhiteListForTrafficRecorder) {
            this.urlWhiteListForTrafficRecorder = urlWhiteListForTrafficRecorder;
        }

        boolean isSessionIdMatchStrategy() {
            return sessionIdMatchStrategy;
        }

        public void setSessionIdMatchStrategy(boolean sessionIdMatchStrategy) {
            this.sessionIdMatchStrategy = sessionIdMatchStrategy;
        }

        boolean isRecordContent() {
            return recordContent;
        }

        public void setRecordContent(boolean recordContent) {
            this.recordContent = recordContent;
        }
    }

    public static class Mitm {
        private boolean createOwn;
        private String keyStoreDir;
        private String alias;
        private String password;
        private String organization;
        private String commonName;
        private String organizationalUnitName;
        private String certOrganization;
        private String certOrganizationalUnitName;

        boolean getCreateOwn() {
            return createOwn;
        }

        public void setCreateOwn(boolean createOwn) {
            this.createOwn = createOwn;
        }

        String getKeyStoreDir() {
            return keyStoreDir;
        }

        public void setKeyStoreDir(String keyStoreDir) {
            this.keyStoreDir = keyStoreDir;
        }

        String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        String getCommonName() {
            return commonName;
        }

        public void setCommonName(String commonName) {
            this.commonName = commonName;
        }

        String getOrganizationalUnitName() {
            return organizationalUnitName;
        }

        public void setOrganizationalUnitName(String organizationalUnitName) {
            this.organizationalUnitName = organizationalUnitName;
        }

        String getCertOrganization() {
            return certOrganization;
        }

        public void setCertOrganization(String certOrganization) {
            this.certOrganization = certOrganization;
        }

        String getCertOrganizationalUnitName() {
            return certOrganizationalUnitName;
        }

        public void setCertOrganizationalUnitName(String certOrganizationalUnitName) {
            this.certOrganizationalUnitName = certOrganizationalUnitName;
        }
    }
}
