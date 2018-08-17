package moxproxy.client;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyService;

public class MoxProxyClient implements IMoxProxyService {

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getSessionNetworkTraffic(String sessionId) {
        return null;
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getAllNetworkTraffic() {
        return null;
    }

    @Override
    public String replaceEntry(MoxProxyProcessedTrafficEntry moxProxyRequest) {
        return null;
    }

    @Override
    public String modifyEntry(MoxProxyProcessedTrafficEntry moxProxyRequest) {
        return null;
    }

    @Override
    public void cancelRule(String ruleId) {

    }

    @Override
    public void clearSessionEntries(String sessionId) {

    }

    @Override
    public void clearAllSessionEntries() {

    }

    @Override
    public String createRule(MoxProxyRule moxProxyRule) {
        //TODO Rule creation
        return moxProxyRule.getId();
    }
}
