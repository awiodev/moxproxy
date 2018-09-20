package moxproxy.builders;

import moxproxy.dto.MoxProxyHttpObject;
import moxproxy.dto.MoxProxyRule;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;

public class MoxProxyRuleBuilder extends BaseBuilder<NullType, MoxProxyRuleBuilder, MoxProxyRule, MoxProxyRuleBuilderValidator> {

    private String sessionId;

    private MoxProxyDirection direction;

    private MoxProxyAction action;

    private MoxProxyHttpObjectBuilder httpObjectBuilder;

    //private MoxProxyMatchingStrategyBuilder strategyBuilder;

    public MoxProxyRuleBuilder(){
        super(null, new MoxProxyRuleBuilderValidator());
        httpObjectBuilder = new MoxProxyHttpObjectBuilder(this);
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

    public MoxProxyHttpObjectBuilder withHttpObject(){
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

    MoxProxyHttpObjectBuilder getHttpObjectBuilder() {
        return httpObjectBuilder;
    }

/*    MoxProxyMatchingStrategyBuilder getStrategyBuilder() {
        return strategyBuilder;
    }*/

    @Override
    MoxProxyRule performBuild() {
        MoxProxyHttpObject httpObject = httpObjectBuilder.build();
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
