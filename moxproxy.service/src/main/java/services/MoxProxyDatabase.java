package services;

import dto.MoxProxyProcessedTrafficEntry;
import interfaces.IMoxProxyDatabase;
import org.springframework.stereotype.Service;
import rules.MoxProxyRule;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class MoxProxyDatabase implements IMoxProxyDatabase {

    private ConcurrentMap<String, MoxProxyRule> rulesDatabase;
    private ConcurrentMap<String, MoxProxyProcessedTrafficEntry> processedTrafficDatabase;

    @Override
    public void startDatabase() {
        rulesDatabase = new ConcurrentHashMap();
        processedTrafficDatabase = new ConcurrentHashMap();
    }

    @Override
    public void stopDatabase() {
        rulesDatabase.clear();
        processedTrafficDatabase.clear();
    }

    @Override
    public void cleanProcessedTraffic(String sessionId) {
        processedTrafficDatabase.entrySet().removeIf(p -> p.getValue().getSessionId().equals(sessionId));
    }

    @Override
    public void cleanProcessedTraffic(Date olderThan) {

        processedTrafficDatabase.entrySet().removeIf(p -> p.getValue().getTimestamp().before(olderThan));
    }

    @Override
    public void cleanRule(String ruleId) {
        rulesDatabase.entrySet().removeIf(p -> p.getValue().getId().equals(ruleId));
    }

    @Override
    public void cleanAllProcessedTraffic() {
        processedTrafficDatabase.clear();
    }

    @Override
    public void cleanAllRules() {
        rulesDatabase.clear();
    }

    @Override
    public void cleanRules(String sessionId) {
        rulesDatabase.entrySet().removeIf(p -> p.getValue().getSessionId().equals(sessionId));
    }

    @Override
    public String addRule(MoxProxyRule moxProxyRule) {
        rulesDatabase.put(moxProxyRule.getId(), moxProxyRule);
        return moxProxyRule.getId();
    }

    @Override
    public void addProcessedRequest(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry) {
        processedTrafficDatabase.put(moxProxyProcessedTrafficEntry.getId(), moxProxyProcessedTrafficEntry);
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getProcessedTraffic() {
        return processedTrafficDatabase.values();
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getProcessedTraffic(String sessionId) {
        return processedTrafficDatabase.values().stream().filter(p -> p.getSessionId().equals(sessionId)).collect(Collectors.toList());
    }

    @Override
    public Iterable<MoxProxyRule> findRulesBySessionId(String sessionId) {
        return rulesDatabase.values().stream().filter(p -> p.getSessionId().equals(sessionId)).collect(Collectors.toList());
    }

    @Override
    public Iterable<MoxProxyRule> getAllRules() {
        return rulesDatabase.values();
    }

    @Override
    public MoxProxyRule findRuleByById(String ruleId) {
        return rulesDatabase.get(ruleId);
    }
}
