package moxproxy.interfaces;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.dto.MoxProxySessionIdMatchingStrategy;

public interface IMoxProxyService {

    Iterable<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId);

    Iterable<MoxProxyProcessedTrafficEntry> getAllRequestTraffic();

    Iterable<MoxProxyProcessedTrafficEntry> getSessionResponseTraffic(String sessionId);

    Iterable<MoxProxyProcessedTrafficEntry> getAllResponseTraffic();

    void cancelRule(String ruleId);

    void clearSessionRules(String sessionId);

    void clearSessionEntries(String sessionId);

    void clearAllSessionEntries();

    String createRule(MoxProxyRule moxProxyRule);

    void modifySessionMatchingStrategy(MoxProxySessionIdMatchingStrategy matchingStrategy);

    MoxProxySessionIdMatchingStrategy getSessionMatchingStrategy();
}
