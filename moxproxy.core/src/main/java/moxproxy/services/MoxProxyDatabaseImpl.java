package moxproxy.services;

import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class MoxProxyDatabaseImpl implements MoxProxyDatabase {

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
        processedRequestDatabase.entrySet().removeIf(p -> null != p.getValue().getSessionId() && p.getValue().getSessionId().equals(sessionId));
        processedResponseDatabase.entrySet().removeIf(p -> null != p.getValue().getSessionId() && p.getValue().getSessionId().equals(sessionId));
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
    public void cleanRules(Date olderThan) {
        rulesDatabase.entrySet().removeIf(p -> p.getValue().getDate().before(olderThan));
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
    public List<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic() {
        return new ArrayList<>(processedRequestDatabase.values());
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic(String sessionId) {
        return processedRequestDatabase.values().stream().filter(p -> null != p.getSessionId() && p.getSessionId().equals(sessionId)).collect(Collectors.toList());
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getProcessedResponseTraffic() {
        return new ArrayList<>(processedResponseDatabase.values());
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getProcessedResponseTraffic(String sessionId) {
        return processedResponseDatabase.values().stream().filter(p -> null != p.getSessionId() && p.getSessionId().equals(sessionId)).collect(Collectors.toList());
    }

    @Override
    public List<MoxProxyRule> findRulesBySessionId(String sessionId) {
        return rulesDatabase.values().stream().filter(p -> null != p.getSessionId() && p.getSessionId().equals(sessionId)).collect(Collectors.toList());
    }

    @Override
    public List<MoxProxyRule> getAllRules() {
        return new ArrayList<>(rulesDatabase.values());
    }

    @Override
    public MoxProxyRule findRuleByById(String ruleId) {
        return rulesDatabase.get(ruleId);
    }
}
