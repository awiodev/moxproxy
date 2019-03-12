package moxproxy.webservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

    public Proxy getProxy(){
        return proxy;
    }

    public Mitm getMitm() {
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
        private boolean recordBodies;

        public int getProxyPort() {
            return proxyPort;
        }

        public void setProxyPort(int proxyPort) {
            this.proxyPort = proxyPort;
        }

        public List<String> getUrlWhiteListForTrafficRecorder() {
            return urlWhiteListForTrafficRecorder;
        }

        public void setUrlWhiteListForTrafficRecorder(List<String> urlWhiteListForTrafficRecorder) {
            this.urlWhiteListForTrafficRecorder = urlWhiteListForTrafficRecorder;
        }

        public boolean isSessionIdMatchStrategy() {
            return sessionIdMatchStrategy;
        }

        public void setSessionIdMatchStrategy(boolean sessionIdMatchStrategy) {
            this.sessionIdMatchStrategy = sessionIdMatchStrategy;
        }

        public boolean isRecordBodies() {
            return recordBodies;
        }

        public void setRecordBodies(boolean recordBodies) {
            this.recordBodies = recordBodies;
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

        public boolean getCreateOwn() {
            return createOwn;
        }

        public void setCreateOwn(boolean createOwn) {
            this.createOwn = createOwn;
        }

        public String getKeyStoreDir() {
            return keyStoreDir;
        }

        public void setKeyStoreDir(String keyStoreDir) {
            this.keyStoreDir = keyStoreDir;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getCommonName() {
            return commonName;
        }

        public void setCommonName(String commonName) {
            this.commonName = commonName;
        }

        public String getOrganizationalUnitName() {
            return organizationalUnitName;
        }

        public void setOrganizationalUnitName(String organizationalUnitName) {
            this.organizationalUnitName = organizationalUnitName;
        }

        public String getCertOrganization() {
            return certOrganization;
        }

        public void setCertOrganization(String certOrganization) {
            this.certOrganization = certOrganization;
        }

        public String getCertOrganizationalUnitName() {
            return certOrganizationalUnitName;
        }

        public void setCertOrganizationalUnitName(String certOrganizationalUnitName) {
            this.certOrganizationalUnitName = certOrganizationalUnitName;
        }
    }
}
