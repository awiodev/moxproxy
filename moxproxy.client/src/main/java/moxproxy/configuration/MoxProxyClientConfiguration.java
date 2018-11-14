package moxproxy.configuration;

public class MoxProxyClientConfiguration implements IMoxProxyClientConfiguration {

    private String baseUrl;

    public MoxProxyClientConfiguration(){
        baseUrl = "http://localhost:8080";
    }

    public MoxProxyClientConfiguration(String baseUrl){
        this.baseUrl = baseUrl;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }
}
