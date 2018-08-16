package moxproxy.builders;

import moxproxy.enums.MoxProxyDirection;
import moxproxy.dto.MoxProxyHttpObject;
import moxproxy.dto.MoxProxyRule;

public class MoxProxyRuleBuilder extends BaseBuilder<NullType, MoxProxyRule> {

    private String sessionId;

    private MoxProxyDirection direction;

    private MoxProxyHttpObjectBuilder httpObjectBuilder;

    public MoxProxyRuleBuilder(){
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

    @Override
    public MoxProxyRule build() {

        MoxProxyHttpObject child = httpObjectBuilder.build();

        var rule = new MoxProxyRule();
        rule.setSessionId(sessionId);
        rule.setHttpDirection(direction);
        rule.setMoxProxyHttpObject(child);
        return rule;
    }
}
