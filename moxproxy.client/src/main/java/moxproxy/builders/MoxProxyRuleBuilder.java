package moxproxy.builders;

import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.dto.MoxProxyHttpObject;
import moxproxy.dto.MoxProxyRule;
import moxproxy.validators.MoxProxyRuleBuilderValidator;

public class MoxProxyRuleBuilder extends BaseBuilder<NullType, MoxProxyRuleBuilder, MoxProxyRule, MoxProxyRuleBuilderValidator> {

    private String sessionId;

    private MoxProxyDirection direction;

    private MoxProxyAction action;

    private MoxProxyHttpObjectBuilder httpObjectBuilder;

    public MoxProxyRuleBuilder(){
        super(null, new MoxProxyRuleBuilderValidator());
        httpObjectBuilder = new MoxProxyHttpObjectBuilder(this);
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

    @Override
    MoxProxyRule performBuild() {
        MoxProxyHttpObject child = httpObjectBuilder.build();
        var rule = new MoxProxyRule();
        rule.setAction(action);
        rule.setSessionId(sessionId);
        rule.setHttpDirection(direction);
        rule.setMoxProxyHttpObject(child);
        return rule;
    }

    @Override
    MoxProxyRuleBuilder getCurrentBuilder() {
        return this;
    }
}
