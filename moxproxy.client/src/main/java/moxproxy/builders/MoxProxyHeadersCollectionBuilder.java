package moxproxy.builders;

import moxproxy.dto.MoxProxyHeader;
import moxproxy.validators.MoxProxyHeadersCollectionBuilderValidator;

import java.util.ArrayList;
import java.util.List;

public class MoxProxyHeadersCollectionBuilder extends CollectionBuilder<MoxProxyHttpObjectBuilder, MoxProxyHeadersCollectionBuilder, MoxProxyHeaderBuilder, Iterable<MoxProxyHeader>, MoxProxyHeadersCollectionBuilderValidator> {

    MoxProxyHeadersCollectionBuilder(MoxProxyHttpObjectBuilder moxProxyHttpObjectBuilder) {
        super(moxProxyHttpObjectBuilder, new MoxProxyHeadersCollectionBuilderValidator());
    }

    @Override
    protected MoxProxyHeaderBuilder CreateChildBuilder() {
        return new MoxProxyHeaderBuilder(this);
    }


    @Override
    Iterable<MoxProxyHeader> performBuild() {
        List<MoxProxyHeaderBuilder> childBuilders = getItems();
        List<MoxProxyHeader> headers = new ArrayList<>();

        childBuilders.forEach(item -> headers.add(item.build()));
        return headers;
    }

    @Override
    MoxProxyHeadersCollectionBuilder getCurrentBuilder() {
        return this;
    }
}
