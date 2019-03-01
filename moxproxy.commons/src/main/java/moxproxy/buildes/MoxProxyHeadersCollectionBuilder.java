package moxproxy.buildes;

import moxproxy.exceptions.BuilderValidationException;
import moxproxy.model.MoxProxyHeader;

import java.util.ArrayList;
import java.util.List;

class MoxProxyHeadersCollectionBuilder extends CollectionBuilder<MoxProxyHttpRuleDefinitionBuilder, MoxProxyHeadersCollectionBuilder, MoxProxyHeaderBuilder, List<MoxProxyHeader>, MoxProxyHeadersCollectionBuilderValidator> {

    private MoxProxyHttpRuleDefinitionBuilder moxProxyHttpRuleDefinitionBuilder;

    MoxProxyHeadersCollectionBuilder(MoxProxyHttpRuleDefinitionBuilder moxProxyHttpRuleDefinitionBuilder) {
        super(moxProxyHttpRuleDefinitionBuilder, new MoxProxyHeadersCollectionBuilderValidator());
        this.moxProxyHttpRuleDefinitionBuilder = moxProxyHttpRuleDefinitionBuilder;
    }

    @Override
    protected MoxProxyHeaderBuilder createChildBuilder() {
        return new MoxProxyHeaderBuilder(moxProxyHttpRuleDefinitionBuilder,this);
    }


    @Override
    protected List<MoxProxyHeader> performBuild() throws BuilderValidationException {
        List<MoxProxyHeaderBuilder> childBuilders = getItems();
        List<MoxProxyHeader> headers = new ArrayList<>();

        childBuilders.forEach(item -> {
            if(null != item.getName()){
                headers.add(item.build());
            }
        });
        return headers;
    }

    @Override
    protected MoxProxyHeadersCollectionBuilder getCurrentBuilder() {
        return this;
    }
}
