package moxproxy.builders;

class MoxProxyHttpObjectBuilderValidator extends BaseBuilderValidator<MoxProxyHttpObjectBuilder> {

    @Override
    public void performValidation(MoxProxyHttpObjectBuilder builder) {
        notNull(builder.getMethod(), getClassName(builder), "METHOD");
        notNull(builder.getPath(), getClassName(builder), "PATH");
    }
}
