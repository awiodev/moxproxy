package moxproxy.builders;

import moxproxy.exceptions.BuilderValidationException;

public class MoxProxyMatchingStrategyBuilderValidator extends BaseBuilderValidator<MoxProxyMatchingStrategyBuilder> {

    @Override
    public void performValidation(MoxProxyMatchingStrategyBuilder builder) throws BuilderValidationException {
        if(!builder.isUseMethod() && !builder.isUsePath() && !builder.isUseSessionId()){
            throw new BuilderValidationException("There should be at least one matching strategy defined for the rule");
        }
    }
}
