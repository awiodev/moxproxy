package moxproxy.builders;

import moxproxy.client.MoxProxyClient;
import moxproxy.configuration.MoxProxyClientConfiguration;
import moxproxy.configuration.MoxProxyClientConfigurationImpl;
import moxproxy.exceptions.BuilderValidationException;
import moxproxy.interfaces.MoxProxyService;

public class MoxProxyClientBuilder extends BaseBuilder<NullType, MoxProxyClientBuilder, MoxProxyService, MoxProxyClientBuilderValidator> {

    private String baseUrl;
    private String user;
    private String password;

    public MoxProxyClientBuilder() {
        super(null, new MoxProxyClientBuilderValidator());
    }

    @Override
    protected MoxProxyService performBuild() throws BuilderValidationException {
        MoxProxyClientConfiguration clientConfiguration = new MoxProxyClientConfigurationImpl(baseUrl, user, password);
        return new MoxProxyClient(clientConfiguration);
    }

    @Override
    protected MoxProxyClientBuilder getCurrentBuilder() {
        return this;
    }

    public MoxProxyClientBuilder withBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
        return this;
    }

    public MoxProxyClientBuilder withUser(String user){
        this.user = user;
        return this;
    }

    public MoxProxyClientBuilder withPassword(String password){
        this.password = password;
        return this;
    }

    String getBaseUrl() {
        return baseUrl;
    }

    String getUser() {
        return user;
    }

    String getPassword() {
        return password;
    }
}
