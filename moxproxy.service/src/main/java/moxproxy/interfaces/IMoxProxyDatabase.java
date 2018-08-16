package moxproxy.interfaces;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;

import java.util.Date;

public interface IMoxProxyDatabase {

    void startDatabase();

    void stopDatabase();

    void cleanProcessedTraffic(String sessionId);

    void cleanProcessedTraffic(Date olderThan);

    void cleanRule(String ruleId);

    void cleanAllProcessedTraffic();

    void cleanAllRules();

    void cleanRules(String sessionId);

    String addRule(MoxProxyRule moxProxyRule);

    void addProcessedRequest(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry);

    Iterable<MoxProxyProcessedTrafficEntry> getProcessedTraffic();

    Iterable<MoxProxyProcessedTrafficEntry> getProcessedTraffic(String sessionId);

    Iterable<MoxProxyRule> findRulesBySessionId(String sessionId);

    Iterable<MoxProxyRule> getAllRules();

    MoxProxyRule findRuleByById(String id);
}
