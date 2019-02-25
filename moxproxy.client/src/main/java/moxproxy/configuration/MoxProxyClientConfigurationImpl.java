package moxproxy.configuration;

public class MoxProxyClientConfigurationImpl implements MoxProxyClientConfiguration {

    private String baseUrl;
    private String user;
    private String password;

    public MoxProxyClientConfigurationImpl(){
        baseUrl = "http://localhost:8081";
        user = "change-user";
        password = "change-password";
    }

    public MoxProxyClientConfigurationImpl(String baseUrl){
        this.baseUrl = baseUrl;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
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
