package interfaces;

import dto.MoxProxyProcessedTrafficEntry;

public interface IMoxProxyService {

    Iterable<MoxProxyProcessedTrafficEntry> getSessionNetworkTraffic(String sessionId);

    Iterable<MoxProxyProcessedTrafficEntry> getAllNetworkTraffic();

    String replaceEntry(MoxProxyProcessedTrafficEntry moxProxyRequest);

    String modifyEntry(MoxProxyProcessedTrafficEntry moxProxyRequest);

    void cancelRule(String actionId);

    void clearSessionEntries(String sessionId);

    void clearAllSessionEntries();
}
