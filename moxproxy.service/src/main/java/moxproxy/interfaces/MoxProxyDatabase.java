package moxproxy.interfaces;

import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;

import java.util.Date;
import java.util.List;

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

    List<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic();

    List<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic(String sessionId);

    List<MoxProxyProcessedTrafficEntry> getProcessedResponseTraffic();

    List<MoxProxyProcessedTrafficEntry> getProcessedResponseTraffic(String sessionId);

    List<MoxProxyRule> findRulesBySessionId(String sessionId);

    List<MoxProxyRule> getAllRules();

    MoxProxyRule findRuleByById(String id);
}
