package moxproxy.builders;

class MoxProxyHttpObjectBuilderValidator extends BaseBuilderValidator<MoxProxyHttpObjectBuilder> {

    @Override
    public void performValidation(MoxProxyHttpObjectBuilder builder) {
        String className = getClassName(builder);
        notNull(builder.getMethod(), className, "METHOD");
        notNull(builder.getPath(), className, "PATH");
    }
}
