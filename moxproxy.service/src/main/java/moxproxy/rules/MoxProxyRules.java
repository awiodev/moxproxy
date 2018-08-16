package moxproxy.rules;

import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.interfaces.IMoxProxyRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MoxProxyRules implements IMoxProxyRules {


    @Autowired
    private IMoxProxyDatabase database;


    @Override
    public Iterable<MoxProxyRule> getRulesBySessionId(String sessionId) {
        return database.findRulesBySessionId(sessionId);
    }

    @Override
    public MoxProxyRule getRuleById(String ruleId) {
        return database.findRuleByById(ruleId);
    }

    @Override
    public void setRule(MoxProxyRule moxProxyRule) {
        database.addRule(moxProxyRule);
    }

    @Override
    public void removeRule(String ruleId) {
        database.cleanRule(ruleId);
    }

    @Override
    public void removeRulesForSessionId(String sessionId) {
        database.cleanRules(sessionId);
    }
}
