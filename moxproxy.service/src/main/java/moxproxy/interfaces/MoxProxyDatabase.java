package moxproxy.interfaces;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;

import java.util.Date;

public interface MoxProxyDatabase {

    void startDatabase();

    void stopDatabase();

    void cleanProcessedTraffic(String sessionId);

    void cleanProcessedTraffic(Date olderThan);

    void cleanRule(String ruleId);

    void cleanAllProcessedTraffic();

    void cleanAllRules();

    void cleanRules(String sessionId);

    void cleanRules(Date olderThan);

    String addRule(MoxProxyRule moxProxyRule);

    void addProcessedRequest(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry);

    void addProcessedResponse(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry);

    Iterable<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic();

    Iterable<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic(String sessionId);

    Iterable<MoxProxyProcessedTrafficEntry> getProcessedResponseTraffic();

    Iterable<MoxProxyProcessedTrafficEntry> getProcessedResponseTraffic(String sessionId);

    Iterable<MoxProxyRule> findRulesBySessionId(String sessionId);

    Iterable<MoxProxyRule> getAllRules();

    MoxProxyRule findRuleByById(String id);
}
