package rules;

import interfaces.IMoxProxyDatabase;
import interfaces.IMoxProxyRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MoxProxyRules implements IMoxProxyRules {

    private IMoxProxyDatabase database;

    @Autowired
    public MoxProxyRules(IMoxProxyDatabase database){
        this.database = database;
    }

    @Override
    public MoxProxyRule getRuleBySessionId(String sessionId) {
        return database.findRuleBySessionId(sessionId);
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
