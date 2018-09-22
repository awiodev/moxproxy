package moxproxy.services;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.interfaces.IMoxProxyRulesMatcher;
import moxproxy.interfaces.IMoxProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoxProxyService implements IMoxProxyService {

    @Autowired
    protected IMoxProxyDatabase database;

    @Autowired
    protected IMoxProxyRulesMatcher matcher;

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId) {
        return database.getProcessedRequestTraffic(sessionId);
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getAllRequestTraffic() {
        return database.getProcessedRequestTraffic();
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getSessionResponseTraffic(String sessionId) {
        return database.getProcessedResponseTraffic(sessionId);
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getAllResponseTraffic() {
        return database.getProcessedResponseTraffic();
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

    @Override
    public void enableSessionIdMatchingStrategy() {
        matcher.useSessionIdMatchingStrategy(true);
    }

    @Override
    public void disableSessionIdMatchingStrategy() {
        matcher.useSessionIdMatchingStrategy(false);
    }
}
