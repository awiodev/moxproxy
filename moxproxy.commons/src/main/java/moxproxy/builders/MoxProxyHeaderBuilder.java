package moxproxy.builders;

import moxproxy.model.MoxProxyHeader;

public class MoxProxyHeaderBuilder extends BaseBuilder<MoxProxyHttpRuleDefinitionBuilder, MoxProxyHeaderBuilder, MoxProxyHeader, MoxProxyHeaderBuilderValidator> {

    private String name;

    private Object value;

    private MoxProxyHeadersCollectionBuilder moxProxyHeadersCollectionBuilder;

    MoxProxyHeaderBuilder(MoxProxyHttpRuleDefinitionBuilder moxProxyHttpRuleDefinitionBuilder, MoxProxyHeadersCollectionBuilder moxProxyHeadersCollectionBuilder) {
        super(moxProxyHttpRuleDefinitionBuilder, new MoxProxyHeaderBuilderValidator());
        this.moxProxyHeadersCollectionBuilder = moxProxyHeadersCollectionBuilder;
    }

    public MoxProxyHeaderBuilder withHeader(String name, Object value){
        this.name = name;
        this.value = value;
        return moxProxyHeadersCollectionBuilder.addItem();
    }

    String getName() {
        return name;
    }

    Object getValue() {
        return value;
    }

    @Override
    protected MoxProxyHeader performBuild() {
        return new MoxProxyHeader(name, value);
    }

    @Override
    protected MoxProxyHeaderBuilder getCurrentBuilder() {
        return this;
    }
}
