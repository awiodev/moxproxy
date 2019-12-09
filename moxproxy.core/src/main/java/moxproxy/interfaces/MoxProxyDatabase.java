package moxproxy.interfaces;

import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;

import java.time.OffsetDateTime;
import java.util.List;

public interface MoxProxyDatabase {

    void startDatabase();

    void stopDatabase();

    void cleanProcessedTraffic(String sessionId);

    void cleanProcessedTraffic(OffsetDateTime olderThan);

    void cleanRule(String ruleId);

    void cleanAllProcessedTraffic();

    void cleanAllRules();

    void cleanRules(String sessionId);

    void cleanRules(OffsetDateTime olderThan);

    String addRule(MoxProxyRule moxProxyRule);

    void addProcessedRequest(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry);

    void addProcessedResponse(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry);

    List<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic();

    List<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic(String sessionId);

    List<MoxProxyProcessedTrafficEntry> getProcessedResponseTraffic();

    List<MoxProxyProcessedTrafficEntry> getProcessedResponseTraffic(String sessionId);

    List<MoxProxyRule> findRulesBySessionId(String sessionId);

    List<MoxProxyRule> getAllRules();

    MoxProxyRule findRuleById(String id);
}
