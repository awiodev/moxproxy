package moxproxy.builders;

import moxproxy.dto.MoxProxyMatchingStrategy;
import moxproxy.exceptions.BuilderValidationException;

public class MoxProxyMatchingStrategyBuilder extends BaseBuilder<MoxProxyRuleBuilder, MoxProxyMatchingStrategyBuilder, MoxProxyMatchingStrategy, MoxProxyMatchingStrategyBuilderValidator> {

    private boolean useSessionId = false;
    private boolean useMethod = false;
    private boolean usePath = false;

    MoxProxyMatchingStrategyBuilder(MoxProxyRuleBuilder builder) {
        super(builder, new MoxProxyMatchingStrategyBuilderValidator());
    }

    public MoxProxyMatchingStrategyBuilder useSessionId(){
        useSessionId = true;
        return this;
    }

    public MoxProxyMatchingStrategyBuilder useMethod(){
        useMethod = true;
        return this;
    }

    public MoxProxyMatchingStrategyBuilder usePath(){
        usePath = true;
        return this;
    }

    boolean isUseSessionId() {
        return useSessionId;
    }

    boolean isUseMethod() {
        return useMethod;
    }

    boolean isUsePath() {
        return usePath;
    }

    @Override
    MoxProxyMatchingStrategy performBuild() throws BuilderValidationException {
        var strategy = new MoxProxyMatchingStrategy();
        strategy.setUseSessionId(useSessionId);
        strategy.setUsePath(usePath);
        strategy.setUseMethod(useMethod);
        return strategy;
    }

    @Override
    MoxProxyMatchingStrategyBuilder getCurrentBuilder() {
        return this;
    }
}
