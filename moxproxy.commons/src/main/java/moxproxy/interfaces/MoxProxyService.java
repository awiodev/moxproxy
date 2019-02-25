package moxproxy.interfaces;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.dto.MoxProxySessionIdMatchingStrategy;

import java.util.List;

public interface MoxProxyService {

    List<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId);

    List<MoxProxyProcessedTrafficEntry> getAllRequestTraffic();

    List<MoxProxyProcessedTrafficEntry> getSessionResponseTraffic(String sessionId);

    List<MoxProxyProcessedTrafficEntry> getAllResponseTraffic();

    void cancelRule(String ruleId);

    void clearSessionRules(String sessionId);

    void clearSessionEntries(String sessionId);

    void clearAllSessionEntries();

    String createRule(MoxProxyRule moxProxyRule);

    void modifySessionMatchingStrategy(MoxProxySessionIdMatchingStrategy matchingStrategy);

    MoxProxySessionIdMatchingStrategy getSessionMatchingStrategy();
}
