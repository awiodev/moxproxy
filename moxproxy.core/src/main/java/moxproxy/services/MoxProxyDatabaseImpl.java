package moxproxy.services;

import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class MoxProxyDatabaseImpl implements MoxProxyDatabase {

    private ConcurrentMap<String, MoxProxyRule> rulesDatabase;
    private ConcurrentMap<String, MoxProxyProcessedTrafficEntry> processedRequestDatabase;
    private ConcurrentMap<String, MoxProxyProcessedTrafficEntry> processedResponseDatabase;

    @Override
    public void startDatabase() {
        rulesDatabase = new ConcurrentHashMap<>(300, 0.9f, 10);
        processedRequestDatabase = new ConcurrentHashMap<>(300, 0.9f, 10);
        processedResponseDatabase = new ConcurrentHashMap<>(300, 0.9f, 10);

        /*rulesDatabase = new ConcurrentHashMap<>();
        processedRequestDatabase = new ConcurrentHashMap<>();
        processedResponseDatabase = new ConcurrentHashMap<>();*/
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
    public void cleanProcessedTraffic(OffsetDateTime olderThan) {

        processedRequestDatabase.entrySet().removeIf(p -> p.getValue().getTimestamp().isBefore(olderThan));
        processedResponseDatabase.entrySet().removeIf(p -> p.getValue().getTimestamp().isBefore(olderThan));
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
    public void cleanRules(OffsetDateTime olderThan) {
        rulesDatabase.entrySet().removeIf(p -> p.getValue().getTimestamp().isBefore(olderThan));
    }

    @Override
    public String addRule(MoxProxyRule moxProxyRule) {
        String id = UUID.randomUUID().toString();
        OffsetDateTime timestamp = OffsetDateTime.now(ZoneOffset.UTC);
        moxProxyRule.updateEntity(id, timestamp);
        rulesDatabase.put(id, moxProxyRule);
        return id;
    }

    @Override
    public void addProcessedRequest(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry) {
        prepareForSave(moxProxyProcessedTrafficEntry);
        processedRequestDatabase.put(moxProxyProcessedTrafficEntry.getId(), moxProxyProcessedTrafficEntry);
    }

    @Override
    public void addProcessedResponse(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry) {
        prepareForSave(moxProxyProcessedTrafficEntry);
        processedResponseDatabase.put(moxProxyProcessedTrafficEntry.getId(), moxProxyProcessedTrafficEntry);
    }

    private void prepareForSave(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry) {
        String id = UUID.randomUUID().toString();
        OffsetDateTime timestamp = OffsetDateTime.now(ZoneOffset.UTC);
        moxProxyProcessedTrafficEntry.updateEntity(id, timestamp);
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic() {
        List<MoxProxyProcessedTrafficEntry> processed = new ArrayList<>();
        processedRequestDatabase.forEach((x, y) -> processed.add(y));
        return processed;
        //return new ArrayList<>(processedRequestDatabase.values());
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getProcessedRequestTraffic(String sessionId) {
        return processedRequestDatabase.values().stream().filter(p -> null != p.getSessionId() && p.getSessionId().equals(sessionId)).collect(Collectors.toList());
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getProcessedResponseTraffic() {
        List<MoxProxyProcessedTrafficEntry> processed = new ArrayList<>();
        processedResponseDatabase.forEach((x, y) -> processed.add(y));
        return processed;
        //return new ArrayList<>(processedResponseDatabase.values());
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
    public MoxProxyRule findRuleById(String ruleId) {
        return rulesDatabase.get(ruleId);
    }
}
