package builders;

import dto.MoxProxyDirection;
import dto.MoxProxyHttpObject;
import dto.MoxProxyRule;

public class MoxProxyRuleBuilder extends BaseBuilder<MoxProxyRule> {

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

    public MoxProxyHttpObjectBuilder withHtmlObject(){
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
