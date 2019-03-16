package moxproxy.builders;

import moxproxy.exceptions.BuilderValidationException;

public class MoxProxyClientBuilderValidator extends BaseBuilderValidator<MoxProxyClientBuilder> {

    @Override
    public void performValidation(MoxProxyClientBuilder builder) throws BuilderValidationException {
        notNull(builder.getBaseUrl(), getClassName(builder), "BASE_URL");
        if(builder.isUseBasicAuth()){
            notNull(builder.getUser(), getClassName(builder), "USER");
            notNull(builder.getPassword(), getClassName(builder), "PASSWORD");
        }
    }
}
