package moxproxy.services;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.interfaces.IMoxProxyDatabase;
import org.springframework.stereotype.Service;
import moxproxy.dto.MoxProxyRule;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class MoxProxyDatabase implements IMoxProxyDatabase {

    private ConcurrentMap<String, MoxProxyRule> rulesDatabase;
    private ConcurrentMap<String, MoxProxyProcessedTrafficEntry> processedRequestDatabase;
    private ConcurrentMap<String, MoxProxyProcessedTrafficEntry> processedResponseDatabase;

    @Override
    public void startDatabase() {
        rulesDatabase = new ConcurrentHashMap<>();
        processedRequestDatabase = new ConcurrentHashMap<>();
        processedResponseDatabase = new ConcurrentHashMap<>();
    }

    @Override
    public void stopDatabase() {
        rulesDatabase.clear();
        processedRequestDatabase.clear();
        processedResponseDatabase.clear();
    }

    @Override
    public void cleanProcessedTraffic(String sessionId) {
        processedRequestDatabase.entrySet().removeIf(p -> p.getValue().getSessionId().equals(sessionId));
        processedResponseDatabase.entrySet().removeIf(p -> p.getValue().getSessionId().equals(sessionId));
    }

    @Override
    public void cleanProcessedTraffic(Date olderThan) {

        processedRequestDatabase.entrySet().removeIf(p -> p.getValue().getTimestamp().before(olderThan));
        processedResponseDatabase.entrySet().removeIf(p -> p.getValue().getTimestamp().before(olderThan));
    }

    @Override
    public void cleanRule(String ruleId) {
        rulesDatabase.entrySet().removeIf(p -> p.getValue().getId().equals(ruleId));
    }

    @Override
    public void cleanAllProcessedTraffic() {
        processedRequestDatabase.clear();
        processedResponseDatabase.clear();
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
        processedRequestDatabase.put(moxProxyProcessedTrafficEntry.getId(), moxProxyProcessedTrafficEntry);
    }

    @Override
    public void addProcessedResponse(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry) {
        processedResponseDatabase.put(moxProxyProcessedTrafficEntry.getId(), moxProxyProcessedTrafficEntry);
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic() {
        return processedRequestDatabase.values();
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic(String sessionId) {
        return processedRequestDatabase.values().stream().filter(p -> p.getSessionId().equals(sessionId)).collect(Collectors.toList());
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getProcessedResponseTraffic() {
        return processedResponseDatabase.values();
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getProcessedResponseTraffic(String sessionId) {
        return processedResponseDatabase.values().stream().filter(p -> p.getSessionId().equals(sessionId)).collect(Collectors.toList());
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
