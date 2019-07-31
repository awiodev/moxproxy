package moxproxy.services;

import moxproxy.interfaces.*;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;
import moxproxy.model.MoxProxySessionIdMatchingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MoxProxyServiceImpl implements MoxProxyService, MoxProxyScheduleFunctionService {

    private static final Logger LOG = LoggerFactory.getLogger(MoxProxyServiceImpl.class);

    MoxProxyDatabase database;

    MoxProxyRulesMatcher matcher;

    protected MoxProxyServiceConfiguration configuration;

    public MoxProxyServiceImpl(MoxProxyDatabase database, MoxProxyRulesMatcher matcher, MoxProxyServiceConfiguration configuration){
        this.database = database;
        this.matcher = matcher;
        this.configuration = configuration;
        this.matcher.useSessionIdMatchingStrategy(configuration.isMatchSessionIdStrategy());
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId) {
        LOG.info("Getting requests trafficentry for session id: {}", sessionId);
        return database.getProcessedRequestTraffic(sessionId);
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getAllRequestTraffic() {
        LOG.info("Getting all requests trafficentry");
        return database.getProcessedRequestTraffic();
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getSessionResponseTraffic(String sessionId) {
        LOG.info("Getting responses trafficentry for session id: {}", sessionId);
        return database.getProcessedResponseTraffic(sessionId);
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getAllResponseTraffic() {
        LOG.info("Getting all responses trafficentry");
        return database.getProcessedResponseTraffic();
    }

    @Override
    public void cancelRule(String ruleId) {
        database.cleanRule(ruleId);
        LOG.info("Removed rule: {}", ruleId);
    }

    @Override
    public void clearSessionRules(String sessionId) {
        database.cleanRules(sessionId);
        LOG.info("Cleared rules for session id: {}", sessionId);
    }

    @Override
    public void clearSessionEntries(String sessionId) {
        database.cleanProcessedTraffic(sessionId);
        database.cleanRules(sessionId);
        LOG.info("Cleared entries for session id: {}", sessionId);
    }

    @Override
    public void clearAllSessionEntries() {
        database.cleanAllProcessedTraffic();
        database.cleanAllRules();
        LOG.info("Cleared all sessions entries");
    }

    @Override
    public String createRule(MoxProxyRule moxProxyRule) {
        String id = database.addRule(moxProxyRule);
        LOG.info("Created rule: {} for session id: {}", id, moxProxyRule.getSessionId());
        return id;
    }

    @Override
    public void modifySessionMatchingStrategy(MoxProxySessionIdMatchingStrategy matchingStrategy) {
        matcher.useSessionIdMatchingStrategy(matchingStrategy.isIncludeSessionIdMatch());
        LOG.info("Session id match modified to: " + matchingStrategy.isIncludeSessionIdMatch());
    }

    @Override
    public MoxProxySessionIdMatchingStrategy getSessionMatchingStrategy() {
        MoxProxySessionIdMatchingStrategy strat = new MoxProxySessionIdMatchingStrategy();
        strat.setIncludeSessionIdMatch(matcher.getSessionIdMatchingStrategy());
        return strat;
    }

    @Override
    public void cleanProcessedTraffic(OffsetDateTime cleanBefore) {
        database.cleanProcessedTraffic(cleanBefore);
        LOG.info("Cleared processed traffic entries older than: {}", parseDate(cleanBefore));
    }

    @Override
    public void cleanRules(OffsetDateTime cleanBefore) {
        database.cleanRules(cleanBefore);
        LOG.info("Cleaning processed rules older than: {}", parseDate(cleanBefore));
    }

    private String parseDate(OffsetDateTime date){
        return date.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
