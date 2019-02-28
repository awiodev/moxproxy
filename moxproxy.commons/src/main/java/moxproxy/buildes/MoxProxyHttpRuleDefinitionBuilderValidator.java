package moxproxy.buildes;

class MoxProxyHttpRuleDefinitionBuilderValidator extends BaseBuilderValidator<MoxProxyHttpRuleDefinitionBuilder> {

    @Override
    public void performValidation(MoxProxyHttpRuleDefinitionBuilder builder) {
        notNull(builder.getMethod(), getClassName(builder), "METHOD");
        notNull(builder.getPathPattern(), getClassName(builder), "PATH_PATTERN");
    }
}
