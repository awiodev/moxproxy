package moxproxy.configuration;

public class MoxProxyClientConfiguration implements IMoxProxyClientConfiguration {

    private String baseUrl;
    private String user;
    private String password;

    public MoxProxyClientConfiguration(){
        baseUrl = "http://localhost:8081";
        user = "change-user";
        password = "change-password";
    }

    public MoxProxyClientConfiguration(String baseUrl){
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
