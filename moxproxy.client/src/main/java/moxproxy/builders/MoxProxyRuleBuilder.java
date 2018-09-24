package moxproxy.builders;

import moxproxy.dto.MoxProxyHttpRuleDefinition;
import moxproxy.dto.MoxProxyRule;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;

public class MoxProxyRuleBuilder extends BaseBuilder<NullType, MoxProxyRuleBuilder, MoxProxyRule, MoxProxyRuleBuilderValidator> {

    private String sessionId;

    private MoxProxyDirection direction;

    private MoxProxyAction action;

    private MoxProxyHttpRuleDefinitionBuilder httpObjectBuilder;

    //private MoxProxyMatchingStrategyBuilder strategyBuilder;

    public MoxProxyRuleBuilder(){
        super(null, new MoxProxyRuleBuilderValidator());
        httpObjectBuilder = new MoxProxyHttpRuleDefinitionBuilder(this);
        //strategyBuilder = new MoxProxyMatchingStrategyBuilder(this);
    }

    public MoxProxyRuleBuilder withSessionId(String sessionId){
        this.sessionId = sessionId;
        return this;
    }

    public MoxProxyRuleBuilder withDirection(MoxProxyDirection direction){
        this.direction = direction;
        return this;
    }

    public MoxProxyHttpRuleDefinitionBuilder withHttpObjectDefinition(){
        return httpObjectBuilder;
    }

    public MoxProxyRuleBuilder withAction(MoxProxyAction action){
        this.action = action;
        return this;
    }

/*    public MoxProxyMatchingStrategyBuilder withMatchingStrategy(){
        return strategyBuilder;
    }*/

    String getSessionId() {
        return sessionId;
    }

    MoxProxyDirection getDirection() {
        return direction;
    }

    MoxProxyAction getAction() {
        return action;
    }

    MoxProxyHttpRuleDefinitionBuilder getHttpObjectBuilder() {
        return httpObjectBuilder;
    }

/*    MoxProxyMatchingStrategyBuilder getStrategyBuilder() {
        return strategyBuilder;
    }*/

    @Override
    MoxProxyRule performBuild() {
        MoxProxyHttpRuleDefinition httpObject = httpObjectBuilder.build();
        //MoxProxyMatchingStrategy strategy = strategyBuilder.build();
        var rule = new MoxProxyRule();
        rule.setAction(action);
        rule.setSessionId(sessionId);
        rule.setHttpDirection(direction);
        rule.setMoxProxyHttpObject(httpObject);
        //rule.setMoxProxyMatchingStrategy(strategy);
        return rule;
    }

    @Override
    MoxProxyRuleBuilder getCurrentBuilder() {
        return this;
    }
}
