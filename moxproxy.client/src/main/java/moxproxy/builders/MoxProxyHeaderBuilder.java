package moxproxy.builders;

import moxproxy.dto.MoxProxyHeader;

public class MoxProxyHeaderBuilder extends BaseBuilder<MoxProxyHeadersCollectionBuilder, MoxProxyHeaderBuilder, MoxProxyHeader, MoxProxyHeaderBuilderValidator> {

    private String name;

    private Object value;

    MoxProxyHeaderBuilder(MoxProxyHeadersCollectionBuilder moxProxyHeadersCollectionBuilder) {
        super(moxProxyHeadersCollectionBuilder, new MoxProxyHeaderBuilderValidator());
    }

    public MoxProxyHeaderBuilder withHeader(String name, Object value){
        this.name = name;
        this.value = value;
        return this;
    }

    String getName() {
        return name;
    }

    Object getValue() {
        return value;
    }

    @Override
    MoxProxyHeader performBuild() {
        var header = new MoxProxyHeader();
        header.setName(name);
        header.setValue(value);
        return header;
    }

    @Override
    MoxProxyHeaderBuilder getCurrentBuilder() {
        return this;
    }
}
