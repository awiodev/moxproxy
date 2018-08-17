package moxproxy.builders;

class MoxProxyHttpObjectBuilderValidator extends BaseBuilderValidator<MoxProxyHttpObjectBuilder> {

    @Override
    public void performValidation(MoxProxyHttpObjectBuilder builder) {
        String className = builder.getClass().getCanonicalName();
        notNull(builder.getMethod(), className, "Method");
        notNull(builder.getPath(), className, "Path");
    }
}
