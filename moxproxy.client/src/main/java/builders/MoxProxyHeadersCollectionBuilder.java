package builders;

import dto.MoxProxyHeader;

import java.util.ArrayList;
import java.util.List;

public class MoxProxyHeadersCollectionBuilder extends CollectionBuilder<MoxProxyHttpObjectBuilder, MoxProxyHeaderBuilder, Iterable<MoxProxyHeader>> {

    MoxProxyHeadersCollectionBuilder(MoxProxyHttpObjectBuilder moxProxyHttpObjectBuilder) {
        super(moxProxyHttpObjectBuilder);
    }

    @Override
    protected MoxProxyHeaderBuilder CreateChildBuilder() {
        return new MoxProxyHeaderBuilder(this);
    }

    @Override
    public Iterable<MoxProxyHeader> build() {
        List<MoxProxyHeaderBuilder> childBuilders = getItems();
        List<MoxProxyHeader> headers = new ArrayList<>();

        childBuilders.forEach(item -> headers.add(item.build()));
        return headers;
    }
}
