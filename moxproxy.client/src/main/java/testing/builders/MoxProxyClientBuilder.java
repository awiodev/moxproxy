package testing.builders;

import moxproxy.client.MoxProxyClient;
import moxproxy.configuration.MoxProxyClientConfiguration;
import moxproxy.configuration.MoxProxyClientConfigurationImpl;
import moxproxy.exceptions.BuilderValidationException;
import moxproxy.interfaces.MoxProxyService;

public class MoxProxyClientBuilder extends BaseBuilder<NullType, MoxProxyClientBuilder, MoxProxyService, MoxProxyClientBuilderValidator> {

    private String baseUrl;
    private boolean useBasicAuth;
    private String user;
    private String password;

    public MoxProxyClientBuilder() {
        super(null, new MoxProxyClientBuilderValidator());
    }

    @Override
    protected MoxProxyService performBuild() throws BuilderValidationException {
        MoxProxyClientConfiguration clientConfiguration = new MoxProxyClientConfigurationImpl(baseUrl, useBasicAuth, user, password);
        return new MoxProxyClient(clientConfiguration);
    }

    @Override
    protected MoxProxyClientBuilder getCurrentBuilder() {
        return this;
    }

    public void withBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
    }

    public void withBasicAuth(){
        useBasicAuth = true;
    }

    public void withUser(String user){
        this.user = user;
    }

    public void withPassword(String password){
        this.password = password;
    }

    String getBaseUrl() {
        return baseUrl;
    }

    boolean isUseBasicAuth() {
        return useBasicAuth;
    }

    String getUser() {
        return user;
    }

    String getPassword() {
        return password;
    }
}
