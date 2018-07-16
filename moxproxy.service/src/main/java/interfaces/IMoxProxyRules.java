package interfaces;

import rules.MoxProxyRule;

public interface IMoxProxyRules {

    MoxProxyRule getRuleBySessionId(String sessionId);

    MoxProxyRule getRuleById(String ruleId);

    void setRule(MoxProxyRule moxProxyRule);

    void removeRule(String ruleId);

    void removeRulesForSessionId(String sessionId);
}
