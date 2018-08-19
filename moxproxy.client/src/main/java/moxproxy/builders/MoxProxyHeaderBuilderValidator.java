package moxproxy.builders;

class MoxProxyHeaderBuilderValidator extends BaseBuilderValidator<MoxProxyHeaderBuilder> {

    @Override
    public void performValidation(MoxProxyHeaderBuilder builder) {
        notNull(builder.getName(), getClassName(builder), "NAME");
    }
}
