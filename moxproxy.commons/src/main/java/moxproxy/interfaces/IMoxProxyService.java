package moxproxy.interfaces;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;

public interface IMoxProxyService {

    Iterable<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId);

    Iterable<MoxProxyProcessedTrafficEntry> getAllRequestTraffic();

    Iterable<MoxProxyProcessedTrafficEntry> getSessionResponseTraffic(String sessionId);

    Iterable<MoxProxyProcessedTrafficEntry> getAllResponseTraffic();

    void cancelRule(String ruleId);

    void clearSessionEntries(String sessionId);

    void clearAllSessionEntries();

    String createRule(MoxProxyRule moxProxyRule);

    void enableSessionIdMatchingStrategy();

    void disableSessionIdMatchingStrategy();
}
