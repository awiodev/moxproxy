package moxproxy.configuration;

public class MoxProxyClientConfigurationImpl implements MoxProxyClientConfiguration {

    private String baseUrl;
    private boolean useBasicAuth;
    private String user;
    private String password;

    public MoxProxyClientConfigurationImpl(){
        baseUrl = "http://localhost:8081";
        user = "change-user";
        password = "change-password";
    }

    public MoxProxyClientConfigurationImpl(String baseUrl, boolean useBasicAuth, String user, String password){
        this.baseUrl = baseUrl;
        this.useBasicAuth = useBasicAuth;
        this.user = user;
        this.password = password;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public boolean useBasicAuth() {
        return useBasicAuth;
    }

    @Override
    public String getUserName() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
