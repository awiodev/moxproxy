package moxproxy.builders;

import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.model.MoxProxyHttpRuleDefinition;
import moxproxy.model.MoxProxyRule;

public class MoxProxyRuleBuilder extends BaseBuilder<NullType, MoxProxyRuleBuilder, MoxProxyRule, MoxProxyRuleBuilderValidator> {

    private String sessionId;

    private MoxProxyDirection direction;

    private MoxProxyAction action;

    private MoxProxyHttpRuleDefinitionBuilder httpObjectBuilder;

    private int invokeLimit;


    public MoxProxyRuleBuilder(){
        super(null, new MoxProxyRuleBuilderValidator());
        httpObjectBuilder = new MoxProxyHttpRuleDefinitionBuilder(this);
        invokeLimit = -1;
    }

    public MoxProxyRuleBuilder withSessionId(String sessionId){
        this.sessionId = sessionId;
        return this;
    }

    public MoxProxyRuleBuilder withDirection(MoxProxyDirection direction){
        this.direction = direction;
        return this;
    }

    public MoxProxyHttpRuleDefinitionBuilder withHttpRuleDefinition(){
        return httpObjectBuilder;
    }

    public MoxProxyRuleBuilder withAction(MoxProxyAction action){
        this.action = action;
        return this;
    }

    public MoxProxyRuleBuilder withInvokeLimit(int invokeLimit){
        this.invokeLimit = invokeLimit;
        return this;
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

    @Override
    protected MoxProxyRule performBuild() {
        MoxProxyHttpRuleDefinition httpObject = httpObjectBuilder.build();
         return new MoxProxyRule(sessionId, direction, httpObject, action, invokeLimit);
    }

    @Override
    protected MoxProxyRuleBuilder getCurrentBuilder() {
        return this;
    }
}
