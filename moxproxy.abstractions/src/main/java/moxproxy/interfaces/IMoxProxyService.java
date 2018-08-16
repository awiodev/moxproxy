package moxproxy.interfaces;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;

public interface IMoxProxyService {

    Iterable<MoxProxyProcessedTrafficEntry> getSessionNetworkTraffic(String sessionId);

    Iterable<MoxProxyProcessedTrafficEntry> getAllNetworkTraffic();

    String replaceEntry(MoxProxyProcessedTrafficEntry moxProxyRequest);

    String modifyEntry(MoxProxyProcessedTrafficEntry moxProxyRequest);

    void cancelRule(String ruleId);

    void clearSessionEntries(String sessionId);

    void clearAllSessionEntries();

    String createRule(MoxProxyRule moxProxyRule);
}
