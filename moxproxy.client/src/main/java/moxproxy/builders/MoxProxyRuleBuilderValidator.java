package moxproxy.builders;

import moxproxy.exceptions.BuilderValidationException;

class MoxProxyRuleBuilderValidator extends BaseBuilderValidator<MoxProxyRuleBuilder>{

    @Override
    public void performValidation(MoxProxyRuleBuilder builder) throws BuilderValidationException {
        validateBasics(builder);
    }

    private void validateBasics(MoxProxyRuleBuilder builder) throws BuilderValidationException {
        String className = getClassName(builder);
        notNull(builder.getDirection(), className, "DIRECTION");
        notNull(builder.getAction(), className, "ACTION");
    }
}
