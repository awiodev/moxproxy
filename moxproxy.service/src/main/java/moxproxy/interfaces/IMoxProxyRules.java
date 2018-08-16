package moxproxy.interfaces;

import moxproxy.dto.MoxProxyRule;

public interface IMoxProxyRules {

    Iterable<MoxProxyRule> getRulesBySessionId(String sessionId);

    MoxProxyRule getRuleById(String ruleId);

    void setRule(MoxProxyRule moxProxyRule);

    void removeRule(String ruleId);

    void removeRulesForSessionId(String sessionId);
}
