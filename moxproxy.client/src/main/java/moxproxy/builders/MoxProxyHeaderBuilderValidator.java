package moxproxy.builders;

class MoxProxyHeaderBuilderValidator extends BaseBuilderValidator<MoxProxyHeaderBuilder> {

    @Override
    public void performValidation(MoxProxyHeaderBuilder builder) {
        String className = getClassName(builder);
        notNull(builder.getName(), className, "NAME");
        notNull(builder.getValue(), className, "VALUE");
    }
}
