package moxproxy.builders;

import moxproxy.buildes.BaseBuilderValidator;
import moxproxy.exceptions.BuilderValidationException;

public class LocalMoxProxyServerBuilderValidator extends BaseBuilderValidator<LocalMoxProxy> {

    @Override
    public void performValidation(LocalMoxProxy localMoxProxy) throws BuilderValidationException {
        notNull(localMoxProxy.getPort(), getClassName(localMoxProxy), "PORT");
    }
}
