package moxproxy.services;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.interfaces.IMoxProxyService;
import org.springframework.beans.factory.annotation.Autowired;

public class MoxProxyService implements IMoxProxyService {

    @Autowired
    IMoxProxyDatabase database;

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getSessionNetworkTraffic(String sessionId) {
        return database.getProcessedTraffic(sessionId);
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getAllNetworkTraffic() {
        return database.getProcessedTraffic();
    }

    @Override
    public void cancelRule(String ruleId) {
        database.cleanRule(ruleId);
    }

    @Override
    public void clearSessionEntries(String sessionId) {
        database.cleanProcessedTraffic(sessionId);
        database.cleanRules(sessionId);
    }

    @Override
    public void clearAllSessionEntries() {
        database.cleanAllProcessedTraffic();
        database.cleanAllRules();
    }

    @Override
    public String createRule(MoxProxyRule moxProxyRule) {
        database.addRule(moxProxyRule);
        return moxProxyRule.getId();
    }
}
