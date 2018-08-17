package moxproxy.builders;

import moxproxy.exceptions.BuilderValidationException;

class MoxProxyRuleBuilderValidator extends BaseBuilderValidator<MoxProxyRuleBuilder>{

    @Override
    public void performValidation(MoxProxyRuleBuilder builder) throws BuilderValidationException {
        validateBasics(builder);
    }

    private void validateBasics(MoxProxyRuleBuilder builder) throws BuilderValidationException {
        String className = builder.getClass().getCanonicalName();
        notNull(builder.getDirection(), className, "Direction");
        notNull(builder.getAction(), className, "Action");
        notNull(builder.getHttpObjectBuilder(), className, "HttpObject");
    }
}
