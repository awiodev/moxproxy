package moxproxy.builders;

import moxproxy.dto.MoxProxyHeader;

public class MoxProxyHeaderBuilder extends BaseBuilder<MoxProxyHeadersCollectionBuilder, MoxProxyHeader> {

    private String name;

    private String value;

    MoxProxyHeaderBuilder(MoxProxyHeadersCollectionBuilder moxProxyHeadersCollectionBuilder) {
        super(moxProxyHeadersCollectionBuilder);
    }

    public MoxProxyHeaderBuilder withName(String name){
        this.name = name;
        return this;
    }

    public MoxProxyHeaderBuilder withValue(String value){
        this.value = value;
        return this;
    }

    @Override
    public MoxProxyHeader build() {
        var header = new MoxProxyHeader();
        header.setName(name);
        header.setValue(value);
        return header;
    }
}
